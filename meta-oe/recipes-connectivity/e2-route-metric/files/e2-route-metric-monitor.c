/* SPDX-License-Identifier: MIT */
/*
 * Small rtnetlink event monitor for e2-route-metric.
 *
 * The process stays blocked in poll(2) while the network is idle. Native
 * link, address and route changes are coalesced and cause the shell route
 * manager to run once. Route changes in the manager's own policy table are
 * ignored to prevent feedback loops.
 */

#define _GNU_SOURCE

#include <errno.h>
#include <fcntl.h>
#include <limits.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#include <poll.h>
#include <signal.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <syslog.h>
#include <time.h>
#include <unistd.h>

#define DEFAULT_TABLE_ID 201U
#define DEFAULT_DEBOUNCE_MS 250U
#define DEFAULT_MANAGER "/usr/sbin/e2-route-metric"
#define RECEIVE_BUFFER_SIZE 32768U

static volatile sig_atomic_t stop_requested;
static volatile sig_atomic_t apply_requested;
static unsigned int managed_table = DEFAULT_TABLE_ID;
static unsigned int debounce_ms = DEFAULT_DEBOUNCE_MS;
static const char *manager_path = DEFAULT_MANAGER;

static void signal_handler(int signo)
{
    if (signo == SIGHUP)
        apply_requested = 1;
    else
        stop_requested = 1;
}

static int install_signal_handlers(void)
{
    struct sigaction action;

    memset(&action, 0, sizeof(action));
    action.sa_handler = signal_handler;
    sigemptyset(&action.sa_mask);

    if (sigaction(SIGTERM, &action, NULL) < 0 ||
        sigaction(SIGINT, &action, NULL) < 0 ||
        sigaction(SIGHUP, &action, NULL) < 0)
        return -1;

    signal(SIGPIPE, SIG_IGN);
    return 0;
}

static int64_t monotonic_milliseconds(void)
{
    struct timespec now;

    if (clock_gettime(CLOCK_MONOTONIC, &now) < 0)
        return 0;

    return (int64_t)now.tv_sec * 1000 + now.tv_nsec / 1000000;
}

static int run_apply(void)
{
    pid_t child;
    pid_t waited;
    int status;

    child = fork();
    if (child < 0) {
        syslog(LOG_ERR, "fork failed: %s", strerror(errno));
        return -1;
    }

    if (child == 0) {
        execl(manager_path, manager_path, "apply", (char *)NULL);
        _exit(127);
    }

    do {
        waited = waitpid(child, &status, 0);
    } while (waited < 0 && errno == EINTR);

    if (waited < 0) {
        syslog(LOG_WARNING, "waitpid failed: %s", strerror(errno));
        return -1;
    }

    if (!WIFEXITED(status) || WEXITSTATUS(status) != 0) {
        syslog(LOG_WARNING, "%s apply failed", manager_path);
        return -1;
    }

    return 0;
}

static unsigned int route_table(const struct nlmsghdr *header)
{
    const struct rtmsg *route;
    struct rtattr *attribute;
    int length;
    unsigned int table;

    if (header->nlmsg_len < NLMSG_LENGTH(sizeof(*route)))
        return RT_TABLE_UNSPEC;

    route = NLMSG_DATA(header);
    table = route->rtm_table;
    length = RTM_PAYLOAD(header);

    for (attribute = RTM_RTA(route); RTA_OK(attribute, length);
         attribute = RTA_NEXT(attribute, length)) {
        if (attribute->rta_type == RTA_TABLE &&
            RTA_PAYLOAD(attribute) >= sizeof(uint32_t)) {
            uint32_t value;

            memcpy(&value, RTA_DATA(attribute), sizeof(value));
            table = value;
            break;
        }
    }

    return table;
}

static bool relevant_message(const struct nlmsghdr *header)
{
    const struct rtmsg *route;

    switch (header->nlmsg_type) {
    case RTM_NEWLINK:
    case RTM_DELLINK:
    case RTM_NEWADDR:
    case RTM_DELADDR:
        return true;

    case RTM_NEWROUTE:
    case RTM_DELROUTE:
        if (header->nlmsg_len < NLMSG_LENGTH(sizeof(*route)))
            return false;
        route = NLMSG_DATA(header);
        if (route->rtm_family != AF_INET && route->rtm_family != AF_INET6)
            return false;
        return route_table(header) != managed_table;

    default:
        return false;
    }
}

static int open_netlink_socket(void)
{
    struct sockaddr_nl address;
    int descriptor;
    int flags;
    int receive_size = RECEIVE_BUFFER_SIZE;

    descriptor = socket(AF_NETLINK, SOCK_RAW | SOCK_CLOEXEC, NETLINK_ROUTE);
    if (descriptor < 0 && errno == EINVAL)
        descriptor = socket(AF_NETLINK, SOCK_RAW, NETLINK_ROUTE);
    if (descriptor < 0)
        return -1;

    flags = fcntl(descriptor, F_GETFD);
    if (flags >= 0)
        fcntl(descriptor, F_SETFD, flags | FD_CLOEXEC);

    flags = fcntl(descriptor, F_GETFL);
    if (flags >= 0)
        fcntl(descriptor, F_SETFL, flags | O_NONBLOCK);

    setsockopt(descriptor, SOL_SOCKET, SO_RCVBUF,
               &receive_size, sizeof(receive_size));

    memset(&address, 0, sizeof(address));
    address.nl_family = AF_NETLINK;
    address.nl_pid = (uint32_t)getpid();
    address.nl_groups = RTMGRP_LINK |
                        RTMGRP_IPV4_IFADDR |
                        RTMGRP_IPV6_IFADDR |
                        RTMGRP_IPV4_ROUTE |
                        RTMGRP_IPV6_ROUTE;

    if (bind(descriptor, (struct sockaddr *)&address, sizeof(address)) < 0) {
        int saved_errno = errno;
        close(descriptor);
        errno = saved_errno;
        return -1;
    }

    return descriptor;
}

static int receive_events(int descriptor, bool *pending, int64_t *deadline)
{
    char buffer[RECEIVE_BUFFER_SIZE];

    for (;;) {
        ssize_t received;
        struct nlmsghdr *header;
        int remaining;

        received = recv(descriptor, buffer, sizeof(buffer), MSG_DONTWAIT);
        if (received < 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK)
                return 0;
            if (errno == EINTR)
                continue;
            if (errno == ENOBUFS) {
                *pending = true;
                *deadline = monotonic_milliseconds() + debounce_ms;
                return 0;
            }
            return -1;
        }

        if (received == 0)
            return -1;

        remaining = (int)received;
        for (header = (struct nlmsghdr *)buffer;
             NLMSG_OK(header, remaining);
             header = NLMSG_NEXT(header, remaining)) {
            if (relevant_message(header)) {
                *pending = true;
                *deadline = monotonic_milliseconds() + debounce_ms;
            }
        }
    }
}

static unsigned int parse_unsigned(const char *text, const char *option)
{
    char *end = NULL;
    unsigned long value;

    errno = 0;
    value = strtoul(text, &end, 10);
    if (errno || !end || *end != '\0' || value > UINT_MAX) {
        fprintf(stderr, "Invalid value for %s: %s\n", option, text);
        exit(EXIT_FAILURE);
    }

    return (unsigned int)value;
}

static void usage(const char *program)
{
    fprintf(stderr,
            "Usage: %s [--table ID] [--debounce-ms MS] [--manager PATH]\n",
            program);
}

static void parse_arguments(int argc, char **argv)
{
    int index;

    for (index = 1; index < argc; ++index) {
        if (!strcmp(argv[index], "--table") && index + 1 < argc) {
            managed_table = parse_unsigned(argv[++index], "--table");
        } else if (!strcmp(argv[index], "--debounce-ms") && index + 1 < argc) {
            debounce_ms = parse_unsigned(argv[++index], "--debounce-ms");
        } else if (!strcmp(argv[index], "--manager") && index + 1 < argc) {
            manager_path = argv[++index];
        } else if (!strcmp(argv[index], "--help")) {
            usage(argv[0]);
            exit(EXIT_SUCCESS);
        } else {
            usage(argv[0]);
            exit(EXIT_FAILURE);
        }
    }
}

int main(int argc, char **argv)
{
    struct pollfd poll_descriptor;
    bool pending = false;
    int64_t deadline = 0;
    int descriptor;

    parse_arguments(argc, argv);
    openlog("e2-route-metric", LOG_PID, LOG_DAEMON);

    if (install_signal_handlers() < 0) {
        syslog(LOG_ERR, "sigaction failed: %s", strerror(errno));
        return EXIT_FAILURE;
    }

    descriptor = open_netlink_socket();
    if (descriptor < 0) {
        syslog(LOG_ERR, "rtnetlink socket failed: %s", strerror(errno));
        return EXIT_FAILURE;
    }

    run_apply();

    poll_descriptor.fd = descriptor;
    poll_descriptor.events = POLLIN;

    while (!stop_requested) {
        int timeout = -1;
        int poll_result;
        int64_t now;

        if (apply_requested) {
            pending = true;
            deadline = monotonic_milliseconds();
            apply_requested = 0;
        }

        now = monotonic_milliseconds();
        if (pending) {
            int64_t remaining = deadline - now;
            if (remaining <= 0)
                timeout = 0;
            else if (remaining > INT_MAX)
                timeout = INT_MAX;
            else
                timeout = (int)remaining;
        }

        poll_result = poll(&poll_descriptor, 1, timeout);
        if (poll_result < 0) {
            if (errno == EINTR)
                continue;
            syslog(LOG_ERR, "poll failed: %s", strerror(errno));
            break;
        }

        if (poll_result > 0) {
            if (poll_descriptor.revents & (POLLIN | POLLERR)) {
                if (receive_events(descriptor, &pending, &deadline) < 0) {
                    syslog(LOG_ERR, "rtnetlink receive failed: %s",
                           strerror(errno));
                    break;
                }
            }
            if (poll_descriptor.revents & (POLLHUP | POLLNVAL))
                break;
        }

        now = monotonic_milliseconds();
        if (pending && now >= deadline) {
            pending = false;
            run_apply();
        }
    }

    close(descriptor);
    closelog();
    return EXIT_SUCCESS;
}
