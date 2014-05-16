/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/select.h>
#include <sys/time.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <sys/ioctl.h>
#include <errno.h>
#include <iostream>
#include <fstream>
#include <mtd/ubi-user.h>

int Select(int maxfd, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout)
{
	int retval;
	fd_set rset, wset, xset;
	timeval interval;

	/* make a backup of all fd_set's and timeval struct */
	if (readfds) rset = *readfds;
	if (writefds) wset = *writefds;
	if (exceptfds) xset = *exceptfds;
	if (timeout)
	{
		interval = *timeout;
	}
	else
	{
		/* make gcc happy... */
		timerclear(&interval);
	}

	while (1)
	{
		retval = ::select(maxfd, readfds, writefds, exceptfds, timeout);

		if (retval < 0)
		{
			/* restore the backup before we continue */
			if (readfds) *readfds = rset;
			if (writefds) *writefds = wset;
			if (exceptfds) *exceptfds = xset;
			if (timeout) *timeout = interval;
			if (errno == EINTR) continue;
			break;
		}

		break;
	}
	return retval;
}

ssize_t singleRead(int fd, void *buf, size_t count)
{
	int retval;
	while (1)
	{
		retval = ::read(fd, buf, count);
		if (retval < 0)
		{
			if (errno == EINTR) continue;
		}
		return retval;
	}
}

ssize_t timedRead(int fd, void *buf, size_t count, int initialtimeout, int interbytetimeout)
{
	fd_set rset;
	struct timeval timeout;
	int result;
	size_t totalread = 0;

	while (totalread < count)
	{
		FD_ZERO(&rset);
		FD_SET(fd, &rset);
		if (totalread == 0)
		{
			timeout.tv_sec = initialtimeout/1000;
			timeout.tv_usec = (initialtimeout%1000) * 1000;
		}
		else
		{
			timeout.tv_sec = interbytetimeout / 1000;
			timeout.tv_usec = (interbytetimeout%1000) * 1000;
		}
		if ((result = select(fd + 1, &rset, NULL, NULL, &timeout)) < 0) return -1; /* error */
		if (result == 0) break;
		if ((result = singleRead(fd, ((char*)buf) + totalread, count - totalread)) < 0)
		{
			return -1;
		}
		if (result == 0) break;
		totalread += result;
	}
	return totalread;
}

ssize_t writeAll(int fd, const void *buf, size_t count)
{
	int retval;
	char *ptr = (char*)buf;
	size_t handledcount = 0;
	while (handledcount < count)
	{
		retval = ::write(fd, &ptr[handledcount], count - handledcount);

		if (retval == 0) return -1;
		if (retval < 0)
		{
			if (errno == EINTR) continue;
			return retval;
		}
		handledcount += retval;
	}
	return handledcount;
}

int find_volumeid(const char *volname)
{
	for (int i = 0; i < 64; i++)
	{
		std::string name;
		std::ifstream file;
		char buffer[256];
		snprintf(buffer, sizeof(buffer), "/sys/class/ubi/ubi0_%d/name", i);
		file.open(buffer);
		if (file.fail())
		{
			continue;
		}
		file >> name;
		if (name == volname)
		{
			return i;
		}
	}
	return -1;
}

int main(int argc, char *argv[])
{
	char buffer[4096];
	int fd;
	long long size;
	if (argc < 3)
	{
		printf("usage: %s <volumename> <filename> [<size>]\n", argv[0]);
		exit(EXIT_FAILURE);
	}
	if (argc < 4)
	{
		struct stat st;
		if (stat(argv[2], &st) < 0)
		{
			std::cerr << "failed to open " << argv[2] << std::endl;
			exit(EXIT_FAILURE);
		}
		size = st.st_size;
	}
	else
	{
		size = atoi(argv[3]);
	}
	int volumeid = find_volumeid(argv[1]);
	if (volumeid >= 0)
	{
		std::cout << "found existing volume, resize to " << size << std::endl;
		int ret;
		struct ubi_rsvol_req req;
		fd = open("/dev/ubi0", O_RDONLY);
		if (fd < 0)
		{
			std::cerr << "failed to open " << "/dev/ubi0" <<  std::endl;
			exit(EXIT_FAILURE);
		}
		req.bytes = size;
		req.vol_id = volumeid;
		ret = ioctl(fd, UBI_IOCRSVOL, &req);
		close(fd);
	}
	else
	{
		std::cout << "create new volume, size " << size << std::endl;
		int ret;
		struct ubi_mkvol_req r;
		size_t n;

		memset(&r, 0, sizeof(struct ubi_mkvol_req));

		r.vol_id = UBI_VOL_NUM_AUTO;
		r.alignment = 1;
		r.bytes = size;
		r.vol_type = UBI_STATIC_VOLUME;

		n = strlen(argv[1]);
		if (n > UBI_MAX_VOLUME_NAME)
		{
			std::cerr << "volume name too long, max " << UBI_MAX_VOLUME_NAME << std::endl;
			exit(EXIT_FAILURE);
		}

		strncpy(r.name, argv[1], UBI_MAX_VOLUME_NAME + 1);
		r.name_len = n;

		fd = open("/dev/ubi0", O_RDONLY);
		if (fd < 0)
		{
			std::cerr << "failed to open " << "/dev/ubi0" <<  std::endl;
			exit(EXIT_FAILURE);
		}

		ret = ioctl(fd, UBI_IOCMKVOL, &r);
		if (ret < 0) 
		{
			close(fd);
			std::cerr << "failed to create volume" <<  std::endl;
			exit(EXIT_FAILURE);
		}

		close(fd);
		volumeid = r.vol_id;
		for (int i = 0; i < 10; i++)
		{
			snprintf(buffer, sizeof(buffer), "/dev/ubi0_%d", volumeid);
			if (access(buffer, F_OK) >= 0) break;
			/* HACK: give udev/mdev some time to create the devicenode */
			usleep(100000);
		}
	}
	snprintf(buffer, sizeof(buffer), "/dev/ubi0_%d", volumeid);
	fd = open(buffer, O_RDWR);
	if (fd < 0)
	{
		std::cerr << "failed to open " << buffer <<  std::endl;
		exit(EXIT_FAILURE);
	}
	ioctl(fd, UBI_IOCVOLUP, &size);
	int in;
	if (!strcmp(argv[2], "-"))
	{
		in = 0;
	}
	else
	{
		in = open(argv[2], O_RDONLY);
	}
	if (in < 0)
	{
		std::cerr << "failed to open " << argv[2] <<  std::endl;
		exit(EXIT_FAILURE);
	}
	while (1)
	{
		int result = timedRead(in, buffer, sizeof(buffer), 5000, 5000);
		if (result <= 0) break;
		if (writeAll(fd, buffer, result) < 0) break;
	}
	exit(EXIT_SUCCESS);
}
