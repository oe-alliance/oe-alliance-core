
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

#define SLEEPTIME 250000
#define true 1
#define false 0

void setMessageToDisplay ( char* );
void show_help();
void scrollText(char* text);
void centeredText(char* text);
void show_animation();

FILE *file_vfd;
char verbose=false;

int main ( int argc, char **argv )
{
	if (argc == 1) {
		show_help();
		return EXIT_FAILURE;
	}

	int i;
	unsigned char centerText=false;
	char* output=0;

	for (i=1; i<argc; i++) {
		char* cmd = argv[i];

		if (strcmp(cmd,"-c")==0) {
			centerText = true;
			if (verbose) printf("option centered text active\n");
		} else if (strcmp(cmd,"-v")==0) {
			verbose = true;
		} else if (strcmp(cmd,"-a")==0) {
			show_animation();
		} else {
			output=cmd;
		}

	}

	if (output) {
		if (strlen(output)>4)
			scrollText(output);
		else {
			if (centerText==true) {
				centeredText(output);
			} else {
				setMessageToDisplay(output);
			}
		}
	}

	return EXIT_SUCCESS;
}


void setMessageToDisplay ( char* str )
{
	if (verbose) printf("setMessageToDisplay %s\n", str);

	if ((file_vfd = fopen("/proc/vfd","w")) == NULL)
	{
		printf ( "vfdctl: could not open vfd-device!\n" );
		exit(-1);
	}

	char* out = malloc(50);
	memset(out, ' ', 4);
	memcpy(out, str, 4);
	strncat(out, "\n", 1);
	fprintf(file_vfd, out);
	if (file_vfd != NULL)
		fclose(file_vfd);
	free(out);
}


void show_help()
{
	printf("\tvfdctl v1.0 - usage:\n\
	\tvfdctl [[-c] text] \n\
	\t-c\tcentered output\n\
	\t-a\tOpenATV\n\
	\t-v\tverbose\n");
}

void centeredText(char* text)
{
	if (verbose) printf("centering text\n");

	int ws = 0, len = strlen(text);
	
	if (len<4)
		ws=(4-len)/2;
	else
		ws=0;

	char *textout = malloc(4);
	memset(textout, ' ', 4);
	memcpy(textout+ws, text, len);
	setMessageToDisplay(textout);
	free(textout);
}

void scrollText(char* text)
{
	if (verbose) printf("scrollText\n");

	int i, len = strlen(text);
	char* out = malloc(4);

	for (i=0; i<=(len-4); i++) {
		memset(out, ' ', 4);
		memcpy(out, text+i, 4);
		setMessageToDisplay(out);
		usleep(SLEEPTIME);
	}

	for (i=1; i<4; i++) {
		memset(out, ' ', 4);
		memcpy(out, text+len+i-4, 4-i);
		setMessageToDisplay(out);
		usleep(SLEEPTIME);
	}

	memset(out, ' ', 4);
	memcpy(out, text, 4);
	setMessageToDisplay(out);
	free (out);
}

void show_animation()
{
	if (verbose) printf("show animation\n");

	int i;
	for (i=0; i<4; i++) {
		usleep(SLEEPTIME);
		setMessageToDisplay("    ");
		usleep(SLEEPTIME);
		setMessageToDisplay(" aaf");
	}
}
