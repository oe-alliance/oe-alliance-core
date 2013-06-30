#ifndef _PNG_UTIL_H_
#define _PNG_UTIL_H_

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <png.h>

#if defined(__cplusplus)
extern "C" {
#endif

using namespace std;

class PNGUtil {
private:
	static PNGUtil *instance;
	int device_fd;

public :
	PNGUtil();
	~PNGUtil();
	int  connect();
	void disconnect();
	int  send(char* png_file_name);
	static PNGUtil *getInstance();
};


#if defined(__cplusplus)
};
#endif

#endif /*_PNG_UTIL_H_*/
