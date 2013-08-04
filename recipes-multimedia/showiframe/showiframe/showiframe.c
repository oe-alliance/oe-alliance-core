/* Unknown license, public domain */
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <errno.h>

#include <linux/dvb/video.h>

void c(int a)
{
	if (a < 0)
	{
		perror("ioctl");
		exit(6);
	}
}

ssize_t write_all(int fd, const void *buf, size_t count)
{
	int retval;
	char *ptr = (char*)buf;
	size_t handledcount = 0;
	while (handledcount < count)
	{
		retval = write(fd, &ptr[handledcount], count - handledcount);

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

int main(int argc, char **argv)
{
	struct stat s;
	if (argc != 2)
	{
		printf("usage: %s <iframe>\n", *argv);
		return 3;
	}

	int f = open(argv[1], O_RDONLY);
	if (f < 0)
	{
		perror(argv[1]);
		return 4;
	}
	fstat(f, &s);

	int fd = open("/dev/dvb/adapter0/video0", O_WRONLY|O_NONBLOCK);

	if (fd <= 0)
	{
		perror("/dev/dvb/adapter0/video0");
		return 2;
	}
	else if (fork() != 0)
		return 0;
	else
	{
		size_t pos=0;
		int seq_end_avail = 0;

		int count = 7;
		/* 0x0 0x0 0x1 0xffffffe0 0x10 0x8 0xffffff80 0xffffff80 0x5 0x21 0x0 0x1 0x0 0x1 */
		
		/* unsigned char pes_header[] = { 0x00, 0x00, 0x01, 0xE0, 0x00, 0x00, 0x80, 0x00, 0x00 }; */

		unsigned char pes_header[] = {0x0, 0x0, 0x1, 0xe0, 0x00, 0x00, 0x80, 0x80, 0x5, 0x21, 0x0, 0x1, 0x0, 0x1};
		
		unsigned char seq_end[] = { 0x00, 0x00, 0x01, 0xB7 };
		unsigned char iframe[s.st_size];
		unsigned char stuffing[8192];
		memset(stuffing, 0, 8192);
		read(f, iframe, s.st_size);

		if(iframe[0] == 0x00 && iframe[1] == 0x00 && iframe[2] == 0x00 && iframe[3] == 0x01 && (iframe[4] & 0x0f) == 0x07)
			ioctl(fd, VIDEO_SET_STREAMTYPE, 1); // set to mpeg4
		else
			ioctl(fd, VIDEO_SET_STREAMTYPE, 0); // set to mpeg2
		c(ioctl(fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_MEMORY));
		c(ioctl(fd, VIDEO_PLAY));
		c(ioctl(fd, VIDEO_CONTINUE));
		c(ioctl(fd, VIDEO_CLEAR_BUFFER));
		while(pos <= (s.st_size-4) && !(seq_end_avail = (!iframe[pos] && !iframe[pos+1] && iframe[pos+2] == 1 && iframe[pos+3] == 0xB7)))
			++pos;
		while(count--){
			if ((iframe[3] >> 4) != 0xE) // no pes header
			{
				write_all(fd, pes_header, sizeof(pes_header));
				usleep(8000);
			}
			else {
				iframe[4] = iframe[5] = 0x00;
			}
			write_all(fd, iframe, s.st_size);
			usleep(8000);
		}
		if (!seq_end_avail)
			write_all(fd, seq_end, sizeof(seq_end));
		write_all(fd, stuffing, 8192);
		usleep(150000);
		c(ioctl(fd, VIDEO_STOP, 0));
		c(ioctl(fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_DEMUX));
	}
	return 0;
}

