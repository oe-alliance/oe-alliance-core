#include "png_util.h"

static int x, y;
static int width, height;

static png_byte color_type;
static png_byte bit_depth;

static png_structp png_ptr;
static png_infop info_ptr;
static int number_of_passes;
static png_bytep * row_pointers = 0;
static png_bytep row_pointers_bit_shift = 0;
static int row_byte_len = 0;

static int read_png_file(char* file_name)
{
	/* 8 is the maximum size that can be checked */
	char header[8] = {0,};

	/* open file and test for it being a png */
	FILE *fp = fopen(file_name, "rb");
	if (!fp)
	{
		fprintf(stderr, "[read_png_file] File %s could not be opened for reading\n", file_name);
		return 0;
	}

	fread(header, 1, 8, fp);
	if (png_sig_cmp((png_byte*)header, 0, 8))
	{
		fclose(fp);
		fprintf(stderr, "[read_png_file] File %s is not recognized as a PNG file\n", file_name);
		return 0;
	}

	/* initialize stuff */
	png_ptr = png_create_read_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);

	if (!png_ptr) 
	{
		fclose(fp);
		fprintf(stderr, "[read_png_file] png_create_read_struct failed\n");
		return 0;
	}

	info_ptr = png_create_info_struct(png_ptr);
	if (!info_ptr)
	{
		png_destroy_read_struct(&png_ptr, NULL, NULL);
		fclose(fp);
		fprintf(stderr, "[read_png_file] png_create_info_struct failed\n");
		return 0;
	}

	if (setjmp(png_jmpbuf(png_ptr)))
	{
		png_destroy_read_struct(&png_ptr, &info_ptr, NULL);
		fclose(fp);
		fprintf(stderr, "[read_png_file] Error during init_io\n");
		return 0;
	}

	png_init_io(png_ptr, fp);
	png_set_sig_bytes(png_ptr, 8);

	png_read_info(png_ptr, info_ptr);

	width = png_get_image_width(png_ptr, info_ptr);
	height = png_get_image_height(png_ptr, info_ptr);
	if (width != 400 || height != 240)
	{
		png_destroy_read_struct(&png_ptr, &info_ptr, NULL);
		fclose(fp);
		fprintf(stderr, "[read_png_file] Error invalid image size\n");
		return 0;
	}
	color_type = png_get_color_type(png_ptr, info_ptr);
	bit_depth = png_get_bit_depth(png_ptr, info_ptr);

	number_of_passes = png_set_interlace_handling(png_ptr);
	png_read_update_info(png_ptr, info_ptr);

        /* read file */
	if (setjmp(png_jmpbuf(png_ptr)))
	{
		png_destroy_read_struct(&png_ptr, &info_ptr, NULL);
		fclose(fp);
		fprintf(stderr, "[read_png_file] Error during read_image\n");
		return 0;
	}

	if(row_pointers == 0)
	{
		row_pointers = (png_bytep*) malloc(sizeof(png_bytep) * height);
		row_byte_len = png_get_rowbytes(png_ptr,info_ptr);
		for (y=0; y<height; y++)
		{
			row_pointers[y] = (png_byte*) malloc(row_byte_len);
		}
		row_pointers_bit_shift = (unsigned char*) malloc(sizeof(unsigned char) * height * width * 2);
	}

	png_read_image(png_ptr, row_pointers);
	png_destroy_read_struct(&png_ptr, &info_ptr, NULL);
	fclose(fp);
	return 1;
}

PNGUtil *PNGUtil::instance = 0;

PNGUtil::PNGUtil() 
{
	instance = this;
	device_fd = -1;
}

PNGUtil::~PNGUtil()
{
	disconnect();
}

PNGUtil *PNGUtil::getInstance()
{
	return instance;
}

int PNGUtil::connect()
{
	const char* device_file_name = "/dev/lcd2";
	device_fd = open("/dev/lcd2", O_RDWR);
	if(device_fd <0)
		return 0;
	return 1;
}

void PNGUtil::disconnect() 
{
	if(device_fd >= 0)
	{
		close(device_fd);
		device_fd = -1;
	}
}

int PNGUtil::send(char* png_file_name) 
{
	if(device_fd < 0) 
	{
		fprintf(stderr, "unloaded!! retry.. connect!!\n");
		return 0;
	}
	if(!read_png_file(png_file_name))
	{
		return 0;
	}
	{
		
		int i,j;
		int row_pointers_ptr = 0;
		int row_pointers_2_ptr = 0;
		
		for(i=0;i<height;i++)
		{
			for(j=0;j<row_byte_len;j+=3)
			{
				row_pointers_bit_shift[row_pointers_2_ptr]=(row_pointers[i][j]&248)|(row_pointers[i][j+1]>>5);
				row_pointers_bit_shift[row_pointers_2_ptr+1]=((row_pointers[i][j+1]&28)<<3)|(row_pointers[i][j+2]>>3);
				row_pointers_2_ptr += 2;
			}
		}
	}
	{
		int w=-1;
		w = write(device_fd, row_pointers_bit_shift, height * width * 2);
		printf("write ret : %d\n",w);
//		ret = ioctl(device_fd, 0);
		printf("write to /dev/lcd2 : %d\n",w);
	}
	return 1;
}

