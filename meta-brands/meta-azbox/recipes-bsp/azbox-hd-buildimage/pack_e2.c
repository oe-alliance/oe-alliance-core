/*
#
# This program is used to build a package file from
#  files obtained from CDK for Azbox
# Usage :       pack_e2 -t "Team name" \
#			-v "Version" \
#			-d "Description" \
#			-a "About" \
#			-K "kernel_descr" \
#			-k kernel_pathname \
#			[-i image_pathname ] \ (if applicable) 
#			[-p patch_pathname ]   ( ./patch.e2 by default)
#
*/
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>
#include <execinfo.h>
#include <signal.h>
#include <string.h>
#include <getopt.h>
#include <arpa/inet.h>
#include <sys/stat.h>

extern char *optarg;
extern int optind, opterr, optopt;

  FILE	*fout;

/* Version info */
  int 	major_version = 0;
  int	minor_version = 0;

/* I-O buffer to copy kernel/image */
  char	buffer[4096];
/* Size of last buffer read */
  int	buff_len;

/* 
*  Internal function file copy
*  output file is already opened
*  input file to copy has to be opened by function
*
* return code = 0 : OK, file has been appended
*             > 0 : Error opening input file, or error during copy
*
*/
int FileCopy(FILE *outputfile, char *file_to_read)
{
	char	done=0;	
	int	resul;
	int 	cumul = 0;
	FILE	*inputfile;

	inputfile=fopen(file_to_read,"r");
	if(inputfile == 0) {
		printf("\nError : cannot open input file <%s>\n", file_to_read);
		return 5;
	}
  
	while( done != 1 ) {
		buff_len=fread((void *)&buffer,1, 4096, inputfile);
		if(buff_len == -1) {
			printf("Read error : %d\n", errno);
			perror("Read error : ");
			resul = -1;
                        done=1;
			break;
		} 
		//printf("Read %d bytes \n", buff_len);
		if(buff_len > 0) {
			int res;
			//printf("Writing %d bytes - Cumul = %d\n", buff_len, cumul);
			res=fwrite((void *) &buffer,1, buff_len, outputfile);
			if(res != buff_len) {
				resul = buff_len - res;
				break;
			}
			cumul+=res;
		} else {
			/* Copy complete */
			resul = 0;
			break;
		}
	}
	close(inputfile);
	return resul;
}

/*
* Internal function GetFileLen
*
*/
int GetFileLen(char *filename)
{
	struct stat		file;
		
	/*----- For information : structure returned by stat
	struct stat
     	{
	      dev_t         st_dev;      //* Peripheral                *
	      ino_t         st_ino;      //* inode                     *
	      mode_t        st_mode;     //* Protect mode              *
	      nlink_t       st_nlink;    //* Number of links           *
	      uid_t         st_uid;      //* UID owner                 *
	      gid_t         st_gid;      //* GID owner                 *
	      dev_t         st_rdev;     //* media type                *
	      off_t         st_size;     //* total size in bytes       *
	      unsigned long st_blksize;  //* block size                *
	      unsigned long st_blocks;   //* size in blocks            *
	      time_t        st_atime;    //* Last access time          *
	      time_t        st_mtime;    //* Last update time          *
	      time_t        st_ctime;    //* Create time               *
	 };
    	----*/

	if(stat(filename,&file) == 0)
	{
		
		return((int)file.st_size);
	}
	/* Error */
	return 0;

}

/* 
* The main section :
*	This program should be invoked by 'buildx' example scripts,
*       	to pass parameters in command line
* 	example :
# Usage :       pack_e2 -t "Team_name" \
#			-v "Version" \
#			-d "Description" \
#			-a "About" \
#			-K "kernel_descr" \
#			-k kernel_pathname \
#			[-i image_pathname ] \ (if applicable) 
#			[-p patch_pathname ]   ( ./patch.e2 by default)
*
*	If no image pathname is passed, that'll generate
*		a 'patch.e2' file containing only kernel
*/

int main (int argc, char *argv[])
{
	long	kernel_size;
	uint32_t size_k;
	int	image_size;
	uint32_t size_i;
	char	dummy[4];

	char	i;
	char 	team_name[11];
	char	version[11];
	char	description[21];
	char	about[13];
	char	kernel_descr[13];
	char	kernel_path[128];
	char	image_path[128];
	char	patch_path[128];
	char	mano[256];
	char	with_image;

  memset(mano, 0, 128);
  memset(dummy, 0, 4);
  memset(team_name, 0, 11);
  memset(version, 0, 11);
  memset(description, 0, 21);
  memset(about, 0, 13);
  memset(kernel_descr, 0, 13);
  memset(kernel_path, 0, 128);
  memset(image_path, 0, 128);
  memset(patch_path, 0, 128);
  with_image=0;
  strcpy(patch_path,"./patch.e2");
  printf("\n\npack_e2 tool for Linux by Doume, version : %d.%d\n", major_version, minor_version);

/* Parse command line parameters */
    if( argc == 1 )
    {
	printf("\nCommand parameters missing \n");
	return 1;
     }

  while ((i=getopt(argc, argv, ":t:v:d:a:K:k:i:p:"))!=EOF)
  {
    switch(i)
    {
      case 't': 
		strncpy((char *)team_name, optarg, 10);
                break;
      case 'v': 
		strncpy((char *)version, optarg, 10);
                break;
      case 'd': 
		strncpy((char *)description, optarg, 20);
                break;
      case 'a': 
		strncpy((char *)about, optarg, 12);
                break;
      case 'k':
		strncpy((char *)kernel_path, optarg, 127);
                break;
      case 'K':
		strncpy((char *)kernel_descr, optarg, 12);
                break;
      case 'i':
		strncpy((char *)image_path, optarg, 127);
                break;
      case 'p':
		strncpy((char *)patch_path, optarg, 127);
                break;
      default : 
	;
    }
  }
  /* Retrieve infos about given files */
  kernel_size = GetFileLen((char *)kernel_path);
  size_k = htonl(kernel_size);
  image_size = GetFileLen((char *)image_path);
  size_i = htonl(image_size);

  /* Display parameters used to create package */
  printf("\nPackaging parameters :\n");
  printf("\tTeam               : <%s>\n", team_name);
  printf("\tImage version      : <%s>\n", version);
  printf("\tDescription        : <%s>\n", description);
  printf("\tAbout              : <%s>\n", about);
  printf("\tKernel file        : <%s> \n\t\t\tsize : %d bytes : 0x%08X\n", 
					kernel_path,
					(int)kernel_size, (int)kernel_size);
  printf("\tKernel description : <%s>\n\n", kernel_descr);
  
  if(image_size > 0) {
	with_image = 1;
  	printf("\tImage  file   : <%s> \n\t\t\tsize : %d bytes : 0x%08X\n", 
					image_path,
					image_size, image_size);
  }
  printf("\n");
  printf("\tResult  file  : <%s>\n", patch_path);
  fflush(stdout);

  /* Now, generate the patch.e2 file ! */

  fout=fopen(patch_path, "w");
  if(fout == NULL) {
	printf("Cannot create resulting file : Abort !\n");
	return 2;
  }
  /* Create header infos ( 56 bytes) */
  if(image_size == 0) {
	/* Only contains kernel, no E2 image ! */
  	fwrite((void *)&dummy, 4, 1, fout);
  } else {
	/* write size of E2 image */
  	fwrite((void *)&size_i, 4, 1, fout);
  }
  /* Write team name */
  fwrite(team_name, 10, 1, fout);
  /* Write description */
  fwrite(description, 20, 1, fout);
  /* Write version */
  fwrite(version, 10, 1, fout);
  /* Write About */
  fwrite(about, 12, 1, fout);
  if(image_size > 0) {
	/* Copy E2 image after header */
	if(FileCopy(fout, image_path) != 0) {
		printf("\nError : cannot append E2 image file to output\n");
		return 4;
	}
  }
  /* In all cases, copy kernel at end of file */

  /* Write kernel size and kernel description */
  fwrite((void *)&size_k, 4, 1, fout);
  fwrite(kernel_descr, 12, 1, fout);
  
  /* And copy kernel file */

  if(FileCopy(fout, kernel_path) != 0) {
	printf("\nError : cannot append kernel file to output\n");
	return 5;
  }
  
  fclose(fout);
  printf("\n\nDone.... Happy TV on Azbox HD\n");

  return 0;


}
