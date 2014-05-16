
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <sys/resource.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/types.h>
#include <linux/input.h>
#include <sys/time.h>
#include <time.h>



int main(int argc, char *argv[])
{

	int kernel_size = 0;
	int image_size = 0;
	char *buf = NULL;
	FILE *final = NULL, *kernel = NULL, *image = NULL;

	if(argc < 4) {
		printf("Usage:%s kernel_zbimage image_jffs2 final_image\n", argv[0]);
		return 0;
	}

	final = fopen(argv[3], "wb");
   	if(!final) {
		printf("Failed to open final image for writing ...\n");
	 	return 0;
	}

	kernel = fopen(argv[1], "rb");
	fseek (kernel, 0, SEEK_END);
	kernel_size = ftell (kernel);
	fseek ( kernel, 0L , SEEK_SET );	
	fwrite(&kernel_size, 1, 4, final);

	image = fopen(argv[2], "rb");
	fseek (image, 0, SEEK_END);
 	image_size = ftell (image);
	fseek ( image , 0L , SEEK_SET );	
	fwrite(&image_size, 1, 4, final);

   
	buf = malloc (kernel_size);
  	fread(buf, 1, kernel_size, kernel);
 	fwrite(buf, 1, kernel_size, final);
 	free(buf);
 	fclose(kernel);
   
	buf = malloc (image_size);
  	fread(buf, 1, image_size, image);
	fwrite(buf,1, image_size, final);
	free(buf);
	fclose(image);
 
	fclose(final);
 	return 0;
}

