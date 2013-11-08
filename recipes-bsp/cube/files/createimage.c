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
#include <fcntl.h>
#include <netinet/in.h>

#define LAYOUTVERSION 2

struct blockheader
{
	unsigned int magic;
	unsigned int size;
	char name[20];
};

struct layoutinfo
{
	unsigned int magic;
	unsigned int structversion;
	unsigned int eraseblocksize;
	char imagename[32];
	struct partitioninfo
	{
		unsigned int type;
		char name[20];
		unsigned int size;
	} partition[10];
} __attribute__((packed));

struct layoutinfo layout;

int main(int argc, char *argv[])
{
	int i;
	unsigned char buffer[4096];
	struct blockheader header;

	if (argc < 7)
	{
		printf("usage: %s eraseblocksize imagename <part0_name> <part0_filename> <part0_size> <part0_type> [..<partN_name> <partN_filename> <partN_size> <partN_type>]\n", argv[0]);
		printf("\ttype 0 = static, 1 = dynamic\n");
		exit(EXIT_FAILURE);
	}
	memset(&layout, 0, sizeof(layout));
	layout.magic = htonl(MAGIC);
	layout.structversion = htonl(LAYOUTVERSION);
	layout.eraseblocksize = htonl(atoi(argv[1]));
	snprintf(layout.imagename, sizeof(layout.imagename), "%s", argv[2]);
	for (i = 0; i < (argc - 3) / 4; i++)
	{
		snprintf(layout.partition[i].name, sizeof(layout.partition[i].name), "%s", argv[3 + 4 * i]);
		layout.partition[i].size = htonl(strtol(argv[3 + 4 * i + 2], NULL, 16));
		/* when partition size is not specified, use file size */
		if (layout.partition[i].size == 0)
		{
			struct stat filestat;
			stat(argv[3 + 4 * i + 1], &filestat);
			layout.partition[i].size = htonl(filestat.st_size);
		}
		layout.partition[i].type = htonl(atoi(argv[3 + 4 * i + 3]));
	}

	header.magic = htonl(MAGIC);
	header.size = htonl(sizeof(layout));
	snprintf(header.name, sizeof(header.name), "layout");
	write(1, &header, sizeof(header));
	write(1, &layout, sizeof(layout));

	for (i = 0; i < (argc - 3) / 4; i++)
	{
		struct stat filestat;
		int size;
		int fd = open(argv[3 + 4 * i + 1], O_RDONLY);
		if (fd < 0) exit(EXIT_FAILURE);
		stat(argv[3 + 4 * i + 1], &filestat);
		header.size = htonl(filestat.st_size);
		snprintf(header.name, sizeof(header.name), "%s", argv[3 + 4 * i]);
		write(1, &header, sizeof(header));
		while (1)
		{
			size = read(fd, buffer, sizeof(buffer));
			if (size <= 0) break;
			if (size > 0)
			{
				write(1, buffer, size);
			}
		}
		close(fd);
	}
	exit(EXIT_SUCCESS);
}
