/*
 * wsdd.c - WS Discovery daemon
 *
 * Copyright (c) 2013 Tobias Waldvogel.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * Anounces a device to Windows via WSD
 */

#define _GNU_SOURCE

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <ctype.h>
#include <errno.h>
#include <unistd.h>
#include <stdarg.h>
#include <syslog.h>

#include <arpa/inet.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <linux/netlink.h>
#include <linux/rtnetlink.h>
#include <net/if.h>
#include <ifaddrs.h>
#include <stdio.h>
#include <sys/socket.h>
#include <unistd.h>

#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <sys/poll.h>

#include <time.h>
#include <pthread.h>
#include <uuid/uuid.h>
#include <signal.h>
 
#define WSD_PORT 3702
#define WSD_HTTP_PORT WSD_PORT
#define WSD_MCAST_ADDR4 ("239.255.255.250")
#define WSD_MCAST_ADDR6 ("FF02::C")

#define MAX_CLIENTS 29
#define WSD_HTTP_SOCK 0
#define WSD_UDP_SOCK 1
#define NETLINK_SOCK 2

#define IPv4 1
#define IPv6 2

static const char wsd_act_hello[] = "Hello";
static const char wsd_act_bye[] = "Bye";
static const char wsd_act_resolve[] = "Resolve";
static const char wsd_act_resolve_matches[] = "ResolveMatches";
static const char wsd_act_probe[] = "Probe";
static const char wsd_act_probe_matches[] = "ProbeMatches";
static const char wsd_act_get[] = "Get";
static const char wsd_act_get_response[] = "GetResponse";
static const char wsd_device[] = "wsdp:Device pub:Computer";

static const char wsd_discovery[] = "http://schemas.xmlsoap.org/ws/2005/04/discovery/";
static const char wsd_transfer[] = "http://schemas.xmlsoap.org/ws/2004/09/transfer/";

static const char wsd_to_anon[] = "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous";
static const char wsd_to_discovery[] = "urn:schemas-xmlsoap-org:ws:2005:04:discovery";

static const char wsd_addr[] = ":Address>";
static const char wsd_msgid[] = ":MessageID>";
static const char wsd_body[] = ":Body>";
static const char wsd_types[] = ":Types>";

struct st_interface
{
	char iface_name[IF_NAMESIZE + 1];
	struct sockaddr_storage *ipv4;
	struct sockaddr_storage *ipv6;
	struct sockaddr_storage *ipv6_link_local;
	struct sockaddr_storage *ipv6_site_local;
	struct sockaddr_storage *ipv6_other;
};

/* global/static variables */
int loglevel = LOG_ERR;
int asdaemon = 1;
int usesyslog = 1;
int terminate = 0;
char endpoint[48];
char sequence[48];
int instance_id;
int ipv46 = 0;
int msg_no = 1;
char in[8192], out[8192];
int g_num_interfaces = 0;
struct st_interface *g_interfaces = NULL;

/* Variable for computer device */
char cd_name[128];
char cd_workgroup[128] = "WORKGROUP";
char cd_friendly_name[128] = "wsdd enabled device";
char cd_url[256] = "http://specs.xmlsoap.org/ws/2006/02/devprof";
char cd_manufacturer[128] = "wsdd";
char cd_model[128] = "wsdd";
char cd_serial[32] = "1";
char cd_firmware[16] = "1.0";

void wsdd_log(int priority, const char* format, ...)
{
	va_list va;
	char printbuf[2048];

	if (priority > loglevel)
		return;

	va_start(va, format);
	vsnprintf(printbuf, sizeof(printbuf), format, va);

	if (usesyslog)
		syslog(priority, "%s", printbuf);
	else
		fprintf(stderr, "%s\n", printbuf);
}

void daemonize(void)
{
	FILE *fp;
	int maxfd;
	int i;

	/* fork #1: exit parent process and continue in the background */
	if ((i = fork()) < 0)
	{
		perror("couldn't fork");
		exit(2);
	} else if (i > 0)
	{
		exit(0);
	}

	/* fork #2: detach from terminal and fork again so we can never regain
	* access to the terminal */
	setsid();
	if ((i = fork()) < 0)
	{
		perror("couldn't fork #2");
		exit(2);
	} else if (i > 0)
	{
		exit(0);
	}

	/* write pid */
	if ((fp=fopen("/var/run/wsdd.pid", "w")) != NULL)
	{
		fprintf(fp, "%d", getpid());
		fclose(fp);
	}

	/* change to root directory and close file descriptors */
	if (chdir("/"))
		wsdd_log(LOG_INFO, "chdir / failed");
	maxfd = getdtablesize();
	for (i = 0; i < maxfd; i++)
		close(i);

	/* use /dev/null for stdin, stdout and stderr */
	open("/dev/null", O_RDONLY);
	open("/dev/null", O_WRONLY);
	open("/dev/null", O_WRONLY);
}

void readSmbConf()
{
	FILE* fp;

	fp = popen("testparm -s -l --parameter-name=\"netbios name\" 2>/dev/null", "r");
	if (fp == NULL) {
		printf("Failed to run testparm\n" );
		return;
	} else {
		fgets(cd_name, sizeof(cd_name)-1, fp);
		pclose(fp);
		if (cd_name[strlen(cd_name) - 1] == '\n') cd_name[strlen(cd_name) - 1] = '\0';
		if (cd_name[strlen(cd_name) - 1] == '\r') cd_name[strlen(cd_name) - 1] = '\0';
	}

	fp = popen("testparm -s -l --parameter-name=\"workgroup\" 2>/dev/null", "r");
	if (fp == NULL) {
		printf("Failed to run testparm\n" );
		return;
	} else {
		fgets(cd_workgroup, sizeof(cd_workgroup)-1, fp);
		pclose(fp);
		if (cd_workgroup[strlen(cd_workgroup) - 1] == '\n') cd_workgroup[strlen(cd_workgroup) - 1] = '\0';
		if (cd_workgroup[strlen(cd_workgroup) - 1] == '\r') cd_workgroup[strlen(cd_workgroup) - 1] = '\0';
	}
}

/* Find xml tag value */
char* get_tag_value(char *xml, const char *tag, int taglen, int *len)
{
	char *val, *end;

	val = strstr(xml, tag);
	if (!val)
		return NULL;

	val += taglen;

	end = strstr(val, "<");
	if (!end)
		return NULL;

	*len = end - val;
	return val;
}

int gen_soap_header(char *buffer, int *len, const char *to, 
				const char* action_pre, const char *action, const char *relates, int http)
{
	static const char wsd_soap_header_start[] =
		"<?xml version=\"1.0\" encoding=\"utf-8\"?>"
		"<soap:Envelope "
			"xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" "
			"xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" "
			"xmlns:wsd=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\" "
			"xmlns:wsx=\"http://schemas.xmlsoap.org/ws/2004/09/mex\" "
			"xmlns:wsdp=\"http://schemas.xmlsoap.org/ws/2006/02/devprof\" "
			"xmlns:un0=\"http://schemas.microsoft.com/windows/pnpx/2005/10\" "
			"xmlns:pub=\"http://schemas.microsoft.com/windows/pub/2005/07\">"
		"<soap:Header>"
			"<wsa:To>%s</wsa:To>"
			"<wsa:Action>%s%s</wsa:Action>"
			"<wsa:MessageID>urn:uuid:%s</wsa:MessageID>";
	static const char wsd_soap_header_relates[] =
			"<wsa:RelatesTo>%s</wsa:RelatesTo>";
	static const char wsd_soap_header_seq[] =
			"<wsd:AppSequence InstanceId=\"%d\" SequenceId=\"%s\" MessageNumber=\"%d\" />";
	static const char wsd_soap_header_end[] =
		"</soap:Header>";

	char msg_id[37];
	uuid_t uu;
	int ret_len;

	uuid_generate_time(uu);
	uuid_unparse(uu, msg_id);

	ret_len = snprintf(buffer, *len, wsd_soap_header_start, to, action_pre, action, msg_id);
	if (ret_len >= *len)
		return -1;

	if (relates)
	{
		ret_len += snprintf(buffer+ret_len, (*len)-ret_len, wsd_soap_header_relates, relates);
		if (ret_len >= *len)
			return -1;
	}

	if (!http)
	{
		ret_len += snprintf(buffer+ret_len, (*len)-ret_len, wsd_soap_header_seq,
				instance_id, sequence, msg_no++);
		if (ret_len >= *len)
			return -1;
	}

	ret_len += snprintf(buffer+ret_len, (*len)-ret_len, wsd_soap_header_end);
	*len = ret_len;
	return 0;
}

int action_hello(char* out, int *out_len, const char* service, int http)
{
	static const char wsd_hello[] =
		"<soap:Body>"
		  "<wsd:Hello>"
			"<wsa:EndpointReference>"
			"<wsa:Address>%s</wsa:Address>"
			"</wsa:EndpointReference>"
			"<wsd:Types>%s</wsd:Types>"
			"<wsd:MetadataVersion>2</wsd:MetadataVersion>"
		  "</wsd:Hello>"
		"</soap:Body>"
	"</soap:Envelope>";

	int ret_len;

	ret_len = *out_len;
	if (gen_soap_header(out, &ret_len, wsd_to_discovery, wsd_discovery, wsd_act_hello, 0, http) < 0)
		return -1;

	ret_len += snprintf(out+ret_len, (*out_len)-ret_len, wsd_hello, endpoint, service);
	*out_len = ret_len;
	wsdd_log(LOG_DEBUG, "action_hello: %s", out);
	return 0;
}

int action_bye(char* out, int *out_len, const char* service, int http)
{
	static const char wsd_bye[] =
		"<soap:Body>"
		  "<wsd:Bye>"
			"<wsa:EndpointReference>"
			"<wsa:Address>%s</wsa:Address>"
			"</wsa:EndpointReference>"
			"<wsd:Types>%s</wsd:Types>"
			"<wsd:MetadataVersion>2</wsd:MetadataVersion>"
		  "</wsd:Bye>"
		"</soap:Body>"
	"</soap:Envelope>";

	int ret_len;

	ret_len = *out_len;
	if (gen_soap_header(out, &ret_len, wsd_to_discovery, wsd_discovery, wsd_act_bye, 0, http) < 0)
		return -1;

	ret_len += snprintf(out+ret_len, (*out_len)-ret_len, wsd_bye, endpoint, service);
	*out_len = ret_len;
	wsdd_log(LOG_DEBUG, "action_bye: %s", out);
	return 0;
}

int action_resolve(char *recv_ip, char *src_msgid, char *body, char* out, int *out_len, int http)
{
	static const char wsd_resolve_match[] =
		"<soap:Body>"
			"<wsd:ResolveMatches>"
				"<wsd:ResolveMatch>"
					"<wsa:EndpointReference>"
						"<wsa:Address>%s</wsa:Address>"
					"</wsa:EndpointReference>"
					"<wsd:Types>%s</wsd:Types>"
					"<wsd:XAddrs>%s</wsd:XAddrs>"
					"<wsd:MetadataVersion>2</wsd:MetadataVersion>"
				"</wsd:ResolveMatch>"
			"</wsd:ResolveMatches>"
		"</soap:Body>"
	"</soap:Envelope>";

	char *endp;
	int endp_len, ret_len;

	endp = get_tag_value(body, wsd_addr, sizeof(wsd_addr)-1, &endp_len);
	if (!endp)
	{
		wsdd_log(LOG_INFO, "action_resolve: Endpoint not found");
		return -1;
	}
	endp[endp_len] = 0;

	if (strcasecmp(endp, endpoint))
		return -1;

	ret_len = *out_len;
	if (gen_soap_header(out, &ret_len, wsd_to_anon, wsd_discovery, wsd_act_resolve_matches, src_msgid, http) < 0)
		return -1;

	ret_len += snprintf(out+ret_len, (*out_len)-ret_len, wsd_resolve_match, endpoint, wsd_device, recv_ip);
	*out_len = ret_len;
	wsdd_log(LOG_DEBUG, "action_resolve: answer %s", out);
	return 0;
}

int action_probe(char *recv_ip, char *src_msgid, char *body, char* out, int *out_len, int http)
{
	static const char wsd_probe_match[] =
		"<soap:Body>"
			"<wsd:ProbeMatches>"
				"<wsd:ProbeMatch>"
					"<wsa:EndpointReference>"
						"<wsa:Address>%s</wsa:Address>"
					"</wsa:EndpointReference>"
					"<wsd:Types>%s</wsd:Types>"
					"<wsd:XAddrs>%s</wsd:XAddrs>"
					"<wsd:MetadataVersion>2</wsd:MetadataVersion>"
				"</wsd:ProbeMatch>"
			"</wsd:ProbeMatches>"
		"</soap:Body>"
	"</soap:Envelope>";

	char *types;
	int types_len, ret_len;

	types = get_tag_value(body, wsd_types, sizeof(wsd_types)-1, &types_len);
	if (!types)
		return -1;

	types[types_len] = 0;

	if (strncmp(types, wsd_device, types_len))
		return -1;

	ret_len = *out_len;
	if (gen_soap_header(out, &ret_len, wsd_to_anon, wsd_discovery, wsd_act_probe_matches, src_msgid, http) < 0)
		return -1;

	ret_len += snprintf(out+ret_len, (*out_len)-ret_len, wsd_probe_match, endpoint, wsd_device, recv_ip);
	*out_len = ret_len;
	wsdd_log(LOG_DEBUG, "action_probe: answer %s", out);
	return 0;
}

int action_get(char *src_msgid, char *out, int *out_len, int http)
{
	static const char wsd_getresponse[] =
	   "<soap:Body>"
		  "<wsx:Metadata>"
			 "<wsx:MetadataSection Dialect=\"http://schemas.xmlsoap.org/ws/2006/02/devprof/ThisDevice\">"
				"<wsdp:ThisDevice>"
				   "<wsdp:FriendlyName>%s</wsdp:FriendlyName>"
				   "<wsdp:FirmwareVersion>%s</wsdp:FirmwareVersion>"
				   "<wsdp:SerialNumber>%s</wsdp:SerialNumber>"
				"</wsdp:ThisDevice>"
			 "</wsx:MetadataSection>"
			 "<wsx:MetadataSection Dialect=\"http://schemas.xmlsoap.org/ws/2006/02/devprof/ThisModel\">"
				"<wsdp:ThisModel>"
				   "<wsdp:Manufacturer>%s</wsdp:Manufacturer>"
				   "<wsdp:ManufacturerUrl>%s</wsdp:ManufacturerUrl>"
				   "<wsdp:ModelName>%s</wsdp:ModelName>"
				   "<wsdp:ModelNumber>1</wsdp:ModelNumber>"
				   "<wsdp:ModelUrl>%s</wsdp:ModelUrl>"
				   "<wsdp:PresentationUrl>%s</wsdp:PresentationUrl>"
				   "<un0:DeviceCategory>Computers</un0:DeviceCategory>"
				"</wsdp:ThisModel>"
			 "</wsx:MetadataSection>"
			 "<wsx:MetadataSection Dialect=\"http://schemas.xmlsoap.org/ws/2006/02/devprof/Relationship\">"
				"<wsdp:Relationship Type=\"http://schemas.xmlsoap.org/ws/2006/02/devprof/host\">"
				   "<wsdp:Host>"
					  "<wsa:EndpointReference>"
						 "<wsa:Address>%s</wsa:Address>"
					  "</wsa:EndpointReference>"
					  "<wsdp:Types>pub:Computer</wsdp:Types>"
					  "<wsdp:ServiceId>%s</wsdp:ServiceId>"
					  "<pub:Computer>%s/Workgroup:%s</pub:Computer>"
				   "</wsdp:Host>"
				"</wsdp:Relationship>"
			 "</wsx:MetadataSection>"
		  "</wsx:Metadata>"
		"</soap:Body>"
	  "</soap:Envelope>";

	int ret_len;

	ret_len = *out_len;
	if (gen_soap_header(out, &ret_len, wsd_to_anon, wsd_transfer, wsd_act_get_response, src_msgid, http) < 0)
	{
		wsdd_log(LOG_DEBUG, "action_get: error gen_soap_header");
		return -1;
	}

	ret_len += snprintf(out+ret_len, (*out_len)-ret_len, wsd_getresponse,
					cd_friendly_name, cd_firmware, cd_serial, cd_manufacturer, cd_url,
					cd_model, cd_url, cd_url, endpoint, endpoint, cd_name, cd_workgroup);
	*out_len = ret_len;
	wsdd_log(LOG_DEBUG, "action_get: answer %s", out);
	return 0;
}

int handle_request(char *recv_ip, char *in, int in_len, char *out, int *out_len, int http)
{
	char *action, *msgid, *body;
	int action_len, msgid_len;

	action = get_tag_value(in, wsd_discovery, sizeof(wsd_discovery)-1, &action_len);
	if (!action)
		action = get_tag_value(in, wsd_transfer, sizeof(wsd_transfer)-1, &action_len);

	if (!action)
	{
		wsdd_log(LOG_DEBUG, "handle_request: no action");
		return -1;
	}

	msgid = get_tag_value(in, wsd_msgid, sizeof(wsd_msgid)-1, &msgid_len);
	if (!msgid)
	{
		wsdd_log(LOG_DEBUG, "handle_request: no message id");
		return -1;
	}

	body = strstr(in, wsd_body);
	if (!body && !http)
	{
		wsdd_log(LOG_DEBUG, "handle_request: no body");
		return -1;
	}

	action[action_len] = 0;
	msgid[msgid_len] = 0;

	wsdd_log(LOG_INFO, "handle_request: Action %s", action);
	if (strcmp(action, wsd_act_probe) == 0)
		return action_probe(recv_ip, msgid, body, out, out_len, http);
	if (strcmp(action, wsd_act_resolve) == 0)
		return action_resolve(recv_ip, msgid, body, out, out_len, http);
	if (strcmp(action, wsd_act_get) == 0)
		return action_get(msgid, out, out_len, http);
	return -1;
}

int wsdd_http_request(int client)
{
	static const char http_response[] =
	"HTTP/1.1 200 OK\r\n" 
	"Content-Type: application/soap+xml\r\n"
	"Server: wsdd\r\n"
	"Date: %s\r\n"
	"Connection: close\r\n"
	"Content-Length: %d\r\n\r\n";

	char header[1024];
	char tstr[32];
	const char *msgid;
	int in_len, out_len, header_len, msgid_len;
	time_t t;

	// receive header
	in_len = recv(client, in, sizeof(in), 0);
	if (in_len < 0)
		return -1;

	wsdd_log(LOG_DEBUG, "wsd_http_request: Incoming HTTP packet");
	wsdd_log(LOG_DEBUG, "wsd_http_request: %s", in);
	in[in_len] = 0;
	out_len = sizeof(out);

	// check whether body is included otherwise try to get it
	msgid = get_tag_value(in, wsd_msgid, sizeof(wsd_msgid)-1, &msgid_len);
	if (!msgid)
	{
		wsdd_log(LOG_DEBUG, "wsd_http_request: No message ID!");
		return 1;
	}

	if (handle_request("", in, in_len, out, &out_len, 1) == 0)
	{
		time(&t);
		strftime(tstr, sizeof(tstr), "%a, %d %b %Y %H:%M:%S GMT", gmtime(&t));
		header_len = snprintf(header, sizeof(header), http_response, tstr, out_len);

		send(client, header, header_len, MSG_MORE);
		send(client, out, out_len, 0);
		return 0;
	}
	wsdd_log(LOG_DEBUG, "wsd_http_request: handle_request error!");
	return -1;
}

int udp_receive(int conn, struct sockaddr_storage *from, int *from_len, int* if_index)
{
	char msg_control[1024];
	struct iovec iovec[1];
	struct msghdr 	msg;
	struct cmsghdr* cmsg;
	int in_len;

	iovec[0].iov_base = in;
	iovec[0].iov_len = sizeof(in);
	msg.msg_name = from;
	msg.msg_namelen = *from_len;
	msg.msg_iov = iovec;
	msg.msg_iovlen = sizeof(iovec) / sizeof(*iovec);
	msg.msg_control = msg_control;
	msg.msg_controllen = sizeof(msg_control);
	msg.msg_flags = 0;

	in_len = recvmsg(conn, &msg, 0);
	if (in_len <= 0)
		return in_len;
	in[in_len] = 0;

	/* Get local interface */
	for (cmsg = CMSG_FIRSTHDR(&msg); cmsg != 0; cmsg = CMSG_NXTHDR(&msg, cmsg))
		if (cmsg->cmsg_level == IPPROTO_IP && cmsg->cmsg_type == IP_PKTINFO)
		{
			struct in_pktinfo* pi = (struct in_pktinfo*)CMSG_DATA(cmsg);
			*if_index = pi->ipi_ifindex;
		} else if (cmsg->cmsg_level == IPPROTO_IPV6 && cmsg->cmsg_type == IPV6_PKTINFO)
		{
			struct in6_pktinfo* pi = (struct in6_pktinfo*)CMSG_DATA(cmsg);
			*if_index = pi->ipi6_ifindex;
		}

	/* Debug */
	char buf[INET6_ADDRSTRLEN];
	memset(buf, 0, INET6_ADDRSTRLEN);
	if (((struct sockaddr*)from)->sa_family == AF_INET)
		inet_ntop(AF_INET, &(((struct sockaddr_in *)from)->sin_addr), buf, INET6_ADDRSTRLEN);
	else if (((struct sockaddr*)from)->sa_family == AF_INET6)
		inet_ntop(AF_INET6, &(((struct sockaddr_in6 *)from)->sin6_addr), buf, INET6_ADDRSTRLEN);
	wsdd_log(LOG_DEBUG, "udp_receive: from %s", buf);

	*from_len = msg.msg_namelen;
	return in_len;
}

int udp_send(int socket, const struct sockaddr* from, int iface, const struct sockaddr *to, int to_len, int out_len)
{
	char msg_control[1024];
	struct iovec iovec;
	struct msghdr 	msg;
	struct cmsghdr* cmsg;
	struct in_pktinfo *pktinfo;
	struct in6_pktinfo *pktinfo6;

	/* Debug */
	char buf[INET6_ADDRSTRLEN];
	memset(buf, 0, INET6_ADDRSTRLEN);
	if (from->sa_family == AF_INET)
		inet_ntop(from->sa_family, &(((struct sockaddr_in *)from)->sin_addr), buf, INET6_ADDRSTRLEN);
	else if (from->sa_family == AF_INET6)
		inet_ntop(from->sa_family, &(((struct sockaddr_in6 *)from)->sin6_addr), buf, INET6_ADDRSTRLEN);
	wsdd_log(LOG_DEBUG, "udp_send: from %s", buf);

	if (to->sa_family == AF_INET)
		inet_ntop(to->sa_family, &(((struct sockaddr_in *)to)->sin_addr), buf, INET6_ADDRSTRLEN);
	else if (to->sa_family == AF_INET6)
		inet_ntop(to->sa_family, &(((struct sockaddr_in6 *)to)->sin6_addr), buf, INET6_ADDRSTRLEN);
	wsdd_log(LOG_DEBUG, "udp_send: to %s", buf);

	/* Send */
	iovec.iov_base = out;
	iovec.iov_len = out_len;
	msg.msg_name = (struct sockaddr*)to;
	msg.msg_namelen = to_len;
	msg.msg_iov = &iovec;
	msg.msg_iovlen = 1;
	msg.msg_control = msg_control;

	if (from->sa_family == AF_INET)
	{
		msg.msg_controllen = CMSG_SPACE(sizeof(struct in_pktinfo));
		msg.msg_flags = 0;

		cmsg = CMSG_FIRSTHDR(&msg);
		cmsg->cmsg_level = IPPROTO_IP;
		cmsg->cmsg_type = IP_PKTINFO;
		cmsg->cmsg_len = CMSG_LEN(sizeof(struct in_pktinfo));
		pktinfo = (struct in_pktinfo*) CMSG_DATA(cmsg);
		pktinfo->ipi_ifindex = 0;
		pktinfo->ipi_spec_dst = ((struct sockaddr_in*)from)->sin_addr;
	}
	else
	{
		msg.msg_controllen = CMSG_SPACE(sizeof(struct in6_pktinfo));
		msg.msg_flags = 0;

		cmsg = CMSG_FIRSTHDR(&msg);
		cmsg->cmsg_level = IPPROTO_IPV6;
		cmsg->cmsg_type = IPV6_PKTINFO;
		cmsg->cmsg_len = CMSG_LEN(sizeof(struct in6_pktinfo));
		pktinfo6 = (struct in6_pktinfo*) CMSG_DATA(cmsg);
		pktinfo6->ipi6_ifindex = ((struct sockaddr_in6*)from)->sin6_scope_id;
		pktinfo6->ipi6_addr = ((struct sockaddr_in6*)from)->sin6_addr;
	}

	int ret = sendmsg(socket, &msg, 0);
	if (ret == -1)
		wsdd_log(LOG_ERR, "udp_send: Failed to send udp packet with %s\n", strerror(errno));

	return ret;
}

int create_hello_bye_message(const char* action, int* out_len)
{
	*out_len = sizeof(out);
	if (strcmp(action, wsd_act_hello) == 0)
		return action_hello(out, out_len, wsd_device, 0);
	else if (strcmp(action, wsd_act_bye) == 0)
		return action_bye(out, out_len, wsd_device, 0);
	return -1;
}

int udp_send_all(const char* action, int socket, char* iface, struct sockaddr_in6 wsd_mcast6, struct sockaddr_in wsd_mcast, int tries)
{
	struct sockaddr* from;
	int i, count;
	int rc = 0;
	int out_len;

	/* Send through all interfaces */
	for (i = 0; i < g_num_interfaces; i++)
	{
		if (strlen(g_interfaces[i].iface_name) == 0)
			continue;
		wsdd_log(LOG_INFO, "udp_send_all: interface: %d", i+1);
		// send ipv4 message
		if (g_interfaces[i].ipv4)
		{
			from = (struct sockaddr*)g_interfaces[i].ipv4;
			if (create_hello_bye_message(action, &out_len) != -1)
			{
				//send may fail if network is not fully up -> retry
				count = 0;
				do
				{
					if (udp_send(socket, from, 0, (struct sockaddr*)&wsd_mcast, sizeof(wsd_mcast), out_len) == -1)
						count++;
					else
						break;
					wsdd_log(LOG_DEBUG, "udp_send_all: udp_send failed -> retry");
					sleep(1);
				} while (count < tries);
				if (count >= tries)
				{
					rc = -1;
					wsdd_log(LOG_ERR, "udp_send_all: All transmission tries failed\n");
				}
			}
			else
				rc = -1;
		}
		// send ipv6 message
		if (g_interfaces[i].ipv6)
		{
			from = (struct sockaddr*)g_interfaces[i].ipv6;
			if (create_hello_bye_message(action, &out_len) != -1)
			{
				//send may fail if network is not fully up -> retry
				count = 0;
				do
				{
					if (udp_send(socket, from, i + 1, (struct sockaddr*)&wsd_mcast6, sizeof(wsd_mcast6), out_len) == -1)
						count++;
					else
						break;
					wsdd_log(LOG_DEBUG, "udp_send_all: udp_send failed -> retry");
					sleep(1);
				} while (count < tries);
				if (count >= tries)
				{
					rc = -1;
					wsdd_log(LOG_ERR, "udp_send_all: All transmission tries failed\n");
				}
			}
			else
				rc = -1;
		}
	}

	return rc;
}

int getXAddrListForInterface(int if_index, int isIPv4, struct sockaddr_storage* to, char* xaddr_list)
{
	char buf[INET6_ADDRSTRLEN];

	if (isIPv4)
	{
		if (g_interfaces[if_index - 1].ipv4)
		{
			sprintf(xaddr_list, "http://%s:%d/wsd/", inet_ntoa(((struct sockaddr_in*)g_interfaces[if_index - 1].ipv4)->sin_addr), WSD_HTTP_PORT);
			memcpy(to, g_interfaces[if_index - 1].ipv4, sizeof(struct sockaddr_storage));
		}
	}
	else
	{
		if (g_interfaces[if_index - 1].ipv6)
		{
			inet_ntop(AF_INET6, &(((struct sockaddr_in6 *)g_interfaces[if_index - 1].ipv6)->sin6_addr), buf, INET6_ADDRSTRLEN);
			sprintf(xaddr_list, "http://[%s]:%d/wsd/", buf, WSD_HTTP_PORT);
			memcpy(to, g_interfaces[if_index - 1].ipv6, sizeof(struct sockaddr_storage));
		}
	}

	if (strlen(xaddr_list) == 0)
	{
		wsdd_log(LOG_ERR, "getXAddrListForInterface: IP not found");
		return -1;
	}

	return 0;
}

void wsd_udp_request(int conn)
{
	struct sockaddr_storage from, to;
	int in_len, out_len, from_len, if_index = 0;
	const int max_xaddr_addresses = 4;
	char xaddr_list[(INET6_ADDRSTRLEN + 2 + 18) * max_xaddr_addresses];
	// + 2 because in xml ipv6 is written like [1:1:....:1] so with []
	// + 18 because of http://...:3702/wsd/

	wsdd_log(LOG_DEBUG, "wsd_udp_request: Incoming UPD packet");

	from_len = sizeof(from);
	in_len = udp_receive(conn, &from, &from_len, &if_index);
	if (in_len <= 0)
		return;

	// Only answer request for user requested IP version
	if (   (ipv46 == IPv4 && !IN6_IS_ADDR_V4MAPPED(&((struct sockaddr_in6*)&from)->sin6_addr))
	    || (ipv46 == IPv6 && IN6_IS_ADDR_V4MAPPED(&((struct sockaddr_in6*)&from)->sin6_addr)))
		return;

	memset(xaddr_list, 0, sizeof(xaddr_list));
	memset(&to, 0, sizeof(struct sockaddr_storage));
	if (getXAddrListForInterface(if_index, IN6_IS_ADDR_V4MAPPED(&((struct sockaddr_in6*)&from)->sin6_addr), &to, xaddr_list) == -1)
	{
		wsdd_log(LOG_ERR, "wsd_udp_request: Error creating XAddr list");
		return;
	}

	out_len = sizeof(out);
	if (handle_request(xaddr_list, in, in_len, out, &out_len, 0) == 0)
		udp_send(conn, (struct sockaddr*)&to, if_index, (struct sockaddr*)&from, from_len, out_len);
}

static int set_multicast(int socket, char* maddr6, char* maddr4, int action6, int action4)
{
	struct ip_mreq mreq;
	struct ipv6_mreq mreq6;
	struct sockaddr_in6 addr;
	int i;
	int rc = 0;

	/* For each interface, add to multicast group */
	for (i = 0; i < g_num_interfaces; i++)
	{
		if (strlen(g_interfaces[i].iface_name) == 0)
			continue;

		if (g_interfaces[i].ipv4)
		{
			mreq.imr_multiaddr.s_addr = inet_addr(maddr4);
			mreq.imr_interface.s_addr = ((struct sockaddr_in *)g_interfaces[i].ipv4)->sin_addr.s_addr;
			if (setsockopt(socket, IPPROTO_IP, action4, &mreq, sizeof(mreq)) < 0)
			{
				wsdd_log(LOG_ERR, "set_multicast: Failed to set IPv4 multicast");
				rc = 1;
			}
		}
		if (g_interfaces[i].ipv6)
		{
			inet_pton(AF_INET6, maddr6, &(addr.sin6_addr));
			mreq6.ipv6mr_multiaddr = addr.sin6_addr;
			mreq6.ipv6mr_interface = i + 1;
			if (setsockopt(socket, IPPROTO_IPV6, action6, &mreq6, sizeof(mreq6)) < 0)
			{
				wsdd_log(LOG_ERR, "set_multicast: Failed to set IPv6 multicast");
				rc = 1;
			}
		}
	}

	return rc;
}

void deleteInterfaceArray()
{
	int i;

	if (g_interfaces)
	{
		for (i = 0; i < g_num_interfaces; i++)
		{
			if (g_interfaces[i].ipv4)
				free(g_interfaces[i].ipv4);
			if (g_interfaces[i].ipv6_link_local)
				free(g_interfaces[i].ipv6_link_local);
			if (g_interfaces[i].ipv6_site_local)
				free(g_interfaces[i].ipv6_site_local);
			if (g_interfaces[i].ipv6_other)
				free(g_interfaces[i].ipv6_other);
		}
		g_num_interfaces = 0;
		free(g_interfaces);
		g_interfaces = NULL;
	}
}

void initInterfaceArray()
{
	int i;

	for (i = 0; i < g_num_interfaces; i++)
	{
		g_interfaces[i].iface_name[0] = '\0';
		g_interfaces[i].ipv4 = NULL;
		g_interfaces[i].ipv6 = NULL;
		g_interfaces[i].ipv6_link_local = NULL;
		g_interfaces[i].ipv6_site_local = NULL;
		g_interfaces[i].ipv6_other = NULL;
	}
}

int getAllInterfacesAndIPs(char* iface_str)
{
	struct ifaddrs *ifap, *ifa;
	struct if_nameindex *if_ni, *if_i;
	unsigned int if_num;
	int retval = 0;

	// procedure can be called multiple times -> free memory
	deleteInterfaceArray();

	// count interfaces
	if_num = 0;
	if_ni = if_nameindex();
	for (if_i = if_ni; ! (if_i->if_index == 0 && if_i->if_name == NULL); if_i++)
		if (if_i->if_index > g_num_interfaces)
			g_num_interfaces = if_i->if_index;
	if_freenameindex(if_ni);

	if (g_num_interfaces == 0)
	{
		wsdd_log(LOG_ERR, "getAllInterfacesAndIPs: Error no interfaces found");
		return -1;
	}

	g_interfaces = malloc(sizeof(struct st_interface) * g_num_interfaces);
	initInterfaceArray();

	if (getifaddrs(&ifap) == -1)
	{
		wsdd_log(LOG_ERR, "getAllInterfacesAndIPs: Failed getifaddrs");
		return -1;
	}

	for (ifa = ifap; ifa != NULL; ifa = ifa->ifa_next)
	{
		if (ifa->ifa_addr == NULL || (strlen(iface_str) != 0 && strcmp(iface_str, ifa->ifa_name) != 0) || (ifa->ifa_flags & IFF_LOOPBACK))
			continue;

		if_num = if_nametoindex(ifa->ifa_name);
		if (if_num == 0)
		{
			wsdd_log(LOG_ERR, "getAllInterfacesAndIPs: No index found for interface %s", ifa->ifa_name);
			retval = -1;
			goto no_index;
		}
		if_num--; // g_interfaces counts from 0
		strncpy(g_interfaces[if_num].iface_name, ifa->ifa_name, IF_NAMESIZE);

		if (ifa->ifa_addr->sa_family == AF_INET && !g_interfaces[if_num].ipv4)
		{
			g_interfaces[if_num].ipv4 = malloc(sizeof(struct sockaddr_storage));
			memcpy(g_interfaces[if_num].ipv4, (struct sockaddr_storage*)ifa->ifa_addr, sizeof(struct sockaddr_storage));
		}
		else if (ifa->ifa_addr->sa_family == AF_INET6)
		{
			if (IN6_IS_ADDR_LINKLOCAL(&((struct sockaddr_in6 *)ifa->ifa_addr)->sin6_addr) && !g_interfaces[if_num].ipv6_link_local)
			{
				g_interfaces[if_num].ipv6_link_local = malloc(sizeof(struct sockaddr_storage));
				memcpy(g_interfaces[if_num].ipv6_link_local, (struct sockaddr_storage*)ifa->ifa_addr, sizeof(struct sockaddr_storage));
			}
			else if (IN6_IS_ADDR_SITELOCAL(&((struct sockaddr_in6 *)ifa->ifa_addr)->sin6_addr))
			{
				g_interfaces[if_num].ipv6_site_local = malloc(sizeof(struct sockaddr_storage));
				memcpy(g_interfaces[if_num].ipv6_site_local, (struct sockaddr_storage*)ifa->ifa_addr, sizeof(struct sockaddr_storage));
			}
			else
			{
				g_interfaces[if_num].ipv6_other = malloc(sizeof(struct sockaddr_storage));
				memcpy(g_interfaces[if_num].ipv6_other, (struct sockaddr_storage*)ifa->ifa_addr, sizeof(struct sockaddr_storage));
			}
		}
	}
no_index:
	freeifaddrs(ifap);

	for (if_num = 0; if_num < g_num_interfaces; if_num++)
	{
		if (strlen(g_interfaces[if_num].iface_name) == 0)
			continue;

		if (g_interfaces[if_num].ipv6_link_local) // preferred IPv6 address
		{
			g_interfaces[if_num].ipv6 = malloc(sizeof(struct sockaddr_storage));
			memcpy(g_interfaces[if_num].ipv6, g_interfaces[if_num].ipv6_link_local, sizeof(struct sockaddr_storage));
		}
		else if (g_interfaces[if_num].ipv6_site_local)
		{
			g_interfaces[if_num].ipv6 = malloc(sizeof(struct sockaddr_storage));
			memcpy(g_interfaces[if_num].ipv6, g_interfaces[if_num].ipv6_site_local, sizeof(struct sockaddr_storage));
		}
		else if (g_interfaces[if_num].ipv6_other)
		{
			g_interfaces[if_num].ipv6 = malloc(sizeof(struct sockaddr_storage));
			memcpy(g_interfaces[if_num].ipv6, g_interfaces[if_num].ipv6_other, sizeof(struct sockaddr_storage));
		}
	}

	return retval;
}

void netlink_recv(int socket, char* iface_str)
{
	char buf[4096];
	struct iovec iov = { buf, sizeof buf };
	struct sockaddr_nl sa;
	struct nlmsghdr *nlh;
	struct msghdr msg = { &sa, sizeof sa, &iov, 1, NULL, 0, 0 };
	ssize_t msglen;

	wsdd_log(LOG_DEBUG, "netlink_recv: processing packet");
	msglen = recvmsg(socket, &msg, 0);

	if (msglen <= 0)
		return;

	for (nlh = (struct nlmsghdr *)buf; NLMSG_OK(nlh, msglen) && nlh->nlmsg_type != NLMSG_DONE; nlh = NLMSG_NEXT(nlh, msglen))
	{
		switch (nlh->nlmsg_type)
		{
			case RTM_NEWADDR:
			case RTM_DELADDR:
				wsdd_log(LOG_DEBUG, "netlink_recv: new/del link or new/del address");
				break;
			default:
				wsdd_log(LOG_DEBUG, "netlink_recv: unknown type %d", nlh->nlmsg_type);
		}
	}
}

int readMachineID(char* uuid_str)
{
	FILE *fp;
	int c, i = 0;
	uuid_t uuid;

	fp = fopen("/etc/machine-id", "r");
	if (fp != NULL)
	{
		while (i < 36 && (c = getc(fp)) != EOF && (isdigit(c) || (islower(c) && isxdigit(c))))
		{
			if (i == 8 || i == 13 || i == 18 || i == 23)
				uuid_str[i++] = '-';
			uuid_str[i++] = c;
		}
		fclose(fp);

		if (i == 36)
		{
			uuid_str[i] = '\0';
		} else
		{
			wsdd_log(LOG_ERR, "/etc/machine-id contains no valid id");
			uuid_str[0] = '\0';
			return -1;
		}
	}
	else
	{
		fp = fopen("/etc/machine-id", "w");
		if (fp == NULL)
		{
			wsdd_log(LOG_ERR, "Cannot read and create /etc/machine-id");
			return -1;
		}
		uuid_generate_random(uuid);
		uuid_unparse_lower(uuid, uuid_str);
		fwrite(uuid_str, 8, 1, fp);
		fwrite(uuid_str + 9, 4, 1, fp);
		fwrite(uuid_str + 14, 4, 1, fp);
		fwrite(uuid_str + 19, 4, 1, fp);
		fwrite(uuid_str + 24, 12, 1, fp);
		fputc('\n', fp);
		fclose(fp);
	}
	return 0;
}

static void sigterm_handler(int sig, siginfo_t *siginfo, void *context)
{
	terminate = 1;
}

/* main function */
int main(int argc, char *argv[])
{
	int opt = 0;
	int retval = EXIT_SUCCESS;
	struct sockaddr_in wsd_mcast;
	struct sockaddr_in6 si6, wsd_mcast6;
	struct sockaddr_nl sa_nl;
	uuid_t uuid;
	char uuid_str[37];
	struct sigaction act;
	static const int enable = 1;
	static const int disable = 0;
	int conn, i, j, activity;
	struct pollfd fds[MAX_CLIENTS + 3];
	char iface[32] = "";
	char c;

	gethostname(cd_name, sizeof(cd_name));
	readSmbConf();

	/* process command line options */
	while ((opt = getopt(argc, argv, "dhFI64n:w:i:")) != -1)
	{
		switch (opt) {

		case 'd':
			loglevel = LOG_DEBUG;
			asdaemon = 0;
			usesyslog = 0;
			break;

		case 'F':
			asdaemon = 0;
			break;

		case 'I':
			usesyslog = 0;
			break;

		case 'i':
			strncpy(iface, optarg, sizeof(iface) - 1);
			break;

		case 'n':
			strncpy(cd_name, optarg, sizeof(cd_name) - 1);
			break;

		case 'w':
			strncpy(cd_workgroup, optarg, sizeof(cd_workgroup) - 1);
			break;

		case '4':
			ipv46 += IPv4;
			break;

		case '6':
			ipv46 += IPv6;
			break;

		case 'h':
			printf("usage: wsdd [-d (debug)] [-I (interactive) [-F (foreground)]\n"
			       "            [-h] [-n hostname] [-w workgroup] [-i interface]\n"
			       "            [-4 IPv4 only] [-6 IPv6 only]\n");
			return(0);
		}
	}

	j=0;
	while (cd_name[j]) {
		c=cd_name[j];
		cd_name[j]=toupper(c);
		j++;
	}

	if (usesyslog)
		openlog("wsdd", LOG_PID, LOG_DAEMON);

	wsdd_log(LOG_INFO, "Starting WSD daemon");
	wsdd_log(LOG_INFO, "Using workgroup: %s", cd_workgroup);
	wsdd_log(LOG_INFO, "Using netbios/host name: %s", cd_name);

	if (*iface)
		if (if_nametoindex(iface) == 0)
		{
			wsdd_log(LOG_ERR, "Invalid interface name %s", iface);
			return EXIT_FAILURE;
		}

	/* Set signal handler for graceful shutdown */
	memset(&act, 0, sizeof(act));
 	act.sa_sigaction = &sigterm_handler;
	act.sa_flags = SA_SIGINFO;
	sigaction(SIGTERM, &act, NULL);
	sigaction(SIGINT, &act, NULL);

	if (asdaemon)
		daemonize();

	/* Generate UUIDs */
	instance_id = time(NULL);
	if (readMachineID(uuid_str) < 0)
		return EXIT_FAILURE;
	sprintf(endpoint, "urn:uuid:%s", uuid_str);
	uuid_generate_time(uuid);
	uuid_unparse(uuid, uuid_str);
	sprintf(sequence, "urn:uuid:%s", uuid_str);
	wsdd_log(LOG_DEBUG, "Using endpoint: %s", endpoint);

	if (getAllInterfacesAndIPs(iface) < 0)
		return EXIT_FAILURE;

	memset((char *) &wsd_mcast, 0, sizeof(wsd_mcast));
	wsd_mcast.sin_family = AF_INET;
	wsd_mcast.sin_port = htons(WSD_PORT);
	if (inet_aton(WSD_MCAST_ADDR4, &wsd_mcast.sin_addr) == 0)
	{
		wsdd_log(LOG_ERR, "inet_aton() failed for wsd ipv4 multicast address");
		return EXIT_FAILURE;
	}
	memset((char *) &wsd_mcast6, 0, sizeof(wsd_mcast6));
	wsd_mcast6.sin6_family = AF_INET6;
	wsd_mcast6.sin6_port = htons(WSD_PORT);
	if (inet_pton(AF_INET6, WSD_MCAST_ADDR6, &wsd_mcast6.sin6_addr) == 0)
	{
		wsdd_log(LOG_ERR, "inet_pton() failed for wsd ipv6 multicast address");
		return EXIT_FAILURE;
	}

	/* Create WSD sockets */
	fds[WSD_UDP_SOCK].fd = socket(AF_INET6, SOCK_DGRAM, IPPROTO_UDP);
	if (fds[WSD_UDP_SOCK].fd == -1)
	{
		wsdd_log(LOG_ERR, "Failed to create wsd udp socket with %s", strerror(errno));
		return EXIT_FAILURE;
	}
	setsockopt(fds[WSD_UDP_SOCK].fd, IPPROTO_IP, IP_PKTINFO, &enable, sizeof(enable));
	setsockopt(fds[WSD_UDP_SOCK].fd, IPPROTO_IPV6, IPV6_V6ONLY, &disable, sizeof(disable));
	setsockopt(fds[WSD_UDP_SOCK].fd, IPPROTO_IPV6, IPV6_PKTINFO, &enable, sizeof(enable));
	setsockopt(fds[WSD_UDP_SOCK].fd, IPPROTO_IPV6, IPV6_RECVPKTINFO, &enable, sizeof(enable));

	memset((char *) &si6, 0, sizeof(si6));
	si6.sin6_family = AF_INET6;
	si6.sin6_port = htons(WSD_PORT);
	si6.sin6_addr = in6addr_any;
	if (bind(fds[WSD_UDP_SOCK].fd, (const struct sockaddr*)&si6, sizeof(si6)) == -1)
	{
		wsdd_log(LOG_ERR, "Failed to listen to UDP port %d for WSDD", ntohs(si6.sin6_port));
		retval = EXIT_FAILURE;
		goto wsd_udp_close;
	}

	if (set_multicast(fds[WSD_UDP_SOCK].fd, WSD_MCAST_ADDR6, WSD_MCAST_ADDR4, IPV6_ADD_MEMBERSHIP, IP_ADD_MEMBERSHIP))
	{
		wsdd_log(LOG_ERR, "Failed to add multicast for WSDD: %s", strerror(errno));
		retval = EXIT_FAILURE;
		goto wsd_drop_multicast;
	}

	fds[WSD_HTTP_SOCK].fd = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);
	if (fds[WSD_HTTP_SOCK].fd == -1)
	{
		wsdd_log(LOG_ERR, "Failed to create http socket with %s", strerror(errno));
		retval = EXIT_FAILURE;
		goto wsd_drop_multicast;
	}
	setsockopt(fds[WSD_HTTP_SOCK].fd, SOL_SOCKET, SO_REUSEADDR, &enable , sizeof(enable));
	setsockopt(fds[WSD_HTTP_SOCK].fd, IPPROTO_IPV6, IPV6_V6ONLY, &disable, sizeof(disable));

	memset((char *) &si6, 0, sizeof(si6));
	si6.sin6_family = AF_INET6;
	si6.sin6_port = htons(WSD_HTTP_PORT);
	si6.sin6_addr = in6addr_any;
	if (bind(fds[WSD_HTTP_SOCK].fd, (const struct sockaddr*)&si6, sizeof(si6)) < 0)
	{
		wsdd_log(LOG_ERR, "Failed to listen to HTTP port %d", WSD_HTTP_PORT);
		retval = EXIT_FAILURE;
		goto wsd_http_close;
	}

	if (listen(fds[WSD_HTTP_SOCK].fd, 5) < 0)
	{
		wsdd_log(LOG_ERR, "Failed to listen to HTTP port %d", WSD_HTTP_PORT);
		retval = EXIT_FAILURE;
		goto wsd_http_close;
	}

	/* Create netlink socket */
	fds[NETLINK_SOCK].fd = socket(AF_NETLINK, SOCK_RAW, NETLINK_ROUTE);
	if (fds[NETLINK_SOCK].fd == -1)
	{
		wsdd_log(LOG_ERR, "Failed to create netlink socket with %s", strerror(errno));
		retval = EXIT_FAILURE;
		goto wsd_http_close;
	}
	memset(&sa_nl, 0, sizeof(sa_nl));
	sa_nl.nl_family = AF_NETLINK;
	sa_nl.nl_groups = 0;
	if (ipv46 == IPv4)
		sa_nl.nl_groups |= RTMGRP_IPV4_IFADDR;
	else if (ipv46 == IPv6)
		sa_nl.nl_groups |= RTMGRP_IPV6_IFADDR;
	else
		sa_nl.nl_groups |= RTMGRP_IPV4_IFADDR | RTMGRP_IPV6_IFADDR;

	if (bind(fds[NETLINK_SOCK].fd, (struct sockaddr *)&sa_nl, sizeof(sa_nl)) == -1)
	{
		wsdd_log(LOG_ERR, "Failed to bind netlink socket with ", strerror(errno));
		retval = EXIT_FAILURE;
		goto wsd_http_close;
	}

	/* Send hello message */
	if (udp_send_all(wsd_act_hello, fds[WSD_UDP_SOCK].fd, iface, wsd_mcast6, wsd_mcast, 7) == -1)
	{
		wsdd_log(LOG_ERR, "Failed to send hello with %s\n", strerror(errno));
		retval = EXIT_FAILURE;
		goto wsd_http_close;
	}

	fds[WSD_HTTP_SOCK].events = POLLIN;
	fds[WSD_UDP_SOCK].events = POLLIN;
	fds[NETLINK_SOCK].events = POLLIN;

	i = 3;
	for (; i < 3 + MAX_CLIENTS; i++)
	{
		fds[i].fd = -1;
		fds[i].events = 0;
	}

	while(!terminate)
	{
		activity = poll(fds , 3 + MAX_CLIENTS, -1);
		if (activity == -1)
		{
			if (errno != EINTR)
				wsdd_log(LOG_ERR, "Select failed");
			continue;
		}

		if (fds[WSD_HTTP_SOCK].revents & POLLIN)
		{
			conn = accept(fds[WSD_HTTP_SOCK].fd, NULL, NULL);
			if (conn < 0)
				continue;

			i = 3;
			while (i < 3 + MAX_CLIENTS && fds[i].fd != -1)
			{
				i++;
			}
			if (i < 3 + MAX_CLIENTS)
			{
				fds[i].fd = conn;
				fds[i].events = POLLIN;
				fds[i].revents = 0;
			} else
				close(conn);
			--activity;
			if (!activity)
				continue;
		}

		if (fds[WSD_UDP_SOCK].revents & POLLIN)
		{
			wsd_udp_request(fds[WSD_UDP_SOCK].fd);
			--activity;
			if (!activity)
				continue;
		}

		if (fds[NETLINK_SOCK].revents & POLLIN)
		{
			netlink_recv(fds[NETLINK_SOCK].fd, iface);
			--activity;
			if (!activity)
				continue;
		}

		i = 3;
		while (i < 3 + MAX_CLIENTS && activity)
		{
			if (fds[i].revents & POLLIN)
			{
				conn = fds[i].fd;
				if (wsdd_http_request(conn) < 1)
				{
					shutdown(conn, SHUT_RDWR);
					close(conn);
					fds[i].fd = -1;
					fds[i].events = 0;
				}
				--activity;
				i++;
			} else
				i++;
		}
	}

	/* Send bye message */
	if (udp_send_all(wsd_act_bye, fds[WSD_UDP_SOCK].fd, iface, wsd_mcast6, wsd_mcast, 1) == -1)
	{
		wsdd_log(LOG_ERR, "Failed to send bye with %s\n", strerror(errno));
		retval = EXIT_FAILURE;
	}

wsd_http_close:
	shutdown(fds[WSD_HTTP_SOCK].fd, SHUT_RDWR);
	close(fds[WSD_HTTP_SOCK].fd);

wsd_drop_multicast:
	set_multicast(fds[WSD_UDP_SOCK].fd, WSD_MCAST_ADDR6, WSD_MCAST_ADDR4, IPV6_DROP_MEMBERSHIP, IP_DROP_MEMBERSHIP);

wsd_udp_close:
	shutdown(fds[WSD_UDP_SOCK].fd, SHUT_RDWR);
	close(fds[WSD_UDP_SOCK].fd);

	deleteInterfaceArray();

	if (asdaemon)
		closelog();

	return retval;
}
