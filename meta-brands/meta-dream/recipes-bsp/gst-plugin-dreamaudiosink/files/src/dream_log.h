#ifndef __DREAM_LOG_H__
#define __DREAM_LOG_H__

#include <stdio.h>
#include <stdarg.h>
#include <time.h>

/* HH:MM:SS.FFFF [dreamaudiosink] <module>: <message> */
static inline void dream_log(const char *module, const char *fmt, ...)
    __attribute__((format(printf, 2, 3)));

static inline void dream_log(const char *module, const char *fmt, ...)
{
    struct timespec ts;
    struct tm tm;
    clock_gettime(CLOCK_REALTIME, &ts);
    localtime_r(&ts.tv_sec, &tm);
    fprintf(stderr, "%02d:%02d:%02d.%04d [dreamaudiosink] %s: ",
            tm.tm_hour, tm.tm_min, tm.tm_sec,
            (int)(ts.tv_nsec / 100000), module);
    va_list ap;
    va_start(ap, fmt);
    vfprintf(stderr, fmt, ap);
    va_end(ap);
    fputc('\n', stderr);
}

#endif
