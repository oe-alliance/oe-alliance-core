#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>

#include <linux/dvb/video.h>

#define VIDEO_STREAMTYPE_MPEG2 0

#ifdef AZBOX
#define VIDEO_SET_STC      _IO('o', 83)
#define VIDEO_STOP_STC     _IO('o', 84)
#define VIDEO_PLAY_STC     _IO('o', 85)
#endif

#ifdef __sh__
#define USE_STILLPICTURE
#endif

static int m_video_clip_fd = -1;

int showSinglePic(const char *filename)
{
#ifdef USE_STILLPICTURE
  printf("USE_STILLPICUTRE\n");
#else /*USE_STILLPICTURE*/
 #ifdef AZBOX
  printf("IS_AZBOX\n");
 #else /*AZBOX*/ /*DREAMBOX*/
  printf("USE_DM_COMPATIBILITY\n");
 #endif /*AZBOX*/
#endif /*USE_STILLPICTURE*/

    {
        printf("showSinglePic %s\n", filename);
        int f = open(filename, O_RDONLY);
        if (f >= 0) {
            struct stat s;
            fstat(f, &s);

            if (m_video_clip_fd == -1) {
                m_video_clip_fd = open("/dev/dvb/adapter0/video0", O_WRONLY|O_NONBLOCK);

                if (ioctl(m_video_clip_fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_MEMORY) < 0)
                        printf("VIDEO_SELECT_SOURCE MEMORY failed (%m)\n");
                if (ioctl(m_video_clip_fd, VIDEO_SET_STREAMTYPE, VIDEO_STREAMTYPE_MPEG2) < 0)
                        printf("VIDEO_SET_STREAMTYPE failed(%m)\n");
#ifdef USE_STILLPICTURE
                if (ioctl(m_video_clip_fd, VIDEO_PLAY) < 0)
                        printf("VIDEO_PLAY failed (%m)\n");
#endif
            }
            if (m_video_clip_fd >= 0) {
            
#ifdef USE_STILLPICTURE
                struct video_still_picture stillpic;
                unsigned char iframe[s.st_size];

                read(f, iframe, s.st_size);

                stillpic.iFrame = iframe;
                stillpic.size = s.st_size;

                if (ioctl(m_video_clip_fd, VIDEO_CLEAR_BUFFER) < 0)
                        printf("video: VIDEO_CLEAR_BUFFER: %m\n");

                if (ioctl(m_video_clip_fd, VIDEO_STILLPICTURE, &stillpic) < 0)
                        printf("VIDEO_STILLPICTURE failed (%m)\n");
#else /*NO USE_STILLPICTURE*/
#ifdef AZBOX
                if (ioctl(m_video_clip_fd, VIDEO_STOP_STC) < 0)
                        printf("VIDEO_STOP_STC failed (%m)\n");
#endif /*AZBOX*/

                if (ioctl(m_video_clip_fd, VIDEO_STOP, 0) < 0)
                        printf("VIDEO_STOP failed (%m)\n");

#ifdef AZBOX
                if (ioctl(m_video_clip_fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_DEMUX) < 0)
                        printf("VIDEO_SELECT_SOURCE DEMUX failed (%m)\n");

                if (ioctl(m_video_clip_fd, VIDEO_SET_STREAMTYPE, VIDEO_STREAMTYPE_MPEG2) < 0)
                        printf("VIDEO_SET_STREAMTYPE failed(%m)\n");

                if (ioctl(m_video_clip_fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_MEMORY) < 0)
                        printf("VIDEO_SELECT_SOURCE MEMORY failed (%m)\n");

                if (ioctl(m_video_clip_fd, VIDEO_SET_STC) < 0)
                        printf("VIDEO_SET_STC failed (%m)\n");
#endif /*AZBOX*/

                if (ioctl(m_video_clip_fd, VIDEO_PLAY) < 0)
                        printf("VIDEO_PLAY failed (%m)\n");

#ifdef AZBOX
                if (ioctl(m_video_clip_fd, VIDEO_PLAY_STC) < 0)
                        printf("VIDEO_PLAY_STC failed (%m)\n");
#endif /*AZBOX*/

                if (ioctl(m_video_clip_fd, VIDEO_CONTINUE) < 0)
                        printf("video: VIDEO_CONTINUE: %m\n");
#ifndef AZBOX
                if (ioctl(m_video_clip_fd, VIDEO_CLEAR_BUFFER) < 0)
                        printf("video: VIDEO_CLEAR_BUFFER: %m\n");
#endif /*NO AZBOX*/

                int seq_end_avail = 0;
                size_t pos = 0;
                unsigned char pes_header[] = { 0x00, 0x00, 0x01 /*Start code*/, 
                                0xE0 /*Stream type (MPEG-1 or MPEG-2 video stream number)*/, 
                                0x00, 0x00, /*PES packet length*/
                                0x80, 0x00, 0x00 /*The extension*/};
                unsigned char seq_end[] = { 0x00, 0x00, 0x01, 0xB7 };
                unsigned char iframe[s.st_size];
                unsigned char stuffing[8192];

                memset(stuffing, 0, 8192);
                read(f, iframe, s.st_size);

                while(pos <= (s.st_size-4) && !(seq_end_avail = (!iframe[pos] && !iframe[pos+1] && iframe[pos+2] == 1 && iframe[pos+3] == 0xB7)))
                    ++pos;
                if ((iframe[3] >> 4) != 0xE) // no pes header
                    write(m_video_clip_fd, pes_header, sizeof(pes_header));
                else
                    iframe[4] = iframe[5] = 0x00;
                write(m_video_clip_fd, iframe, s.st_size);
                if (!seq_end_avail)
                    write(m_video_clip_fd, seq_end, sizeof(seq_end));
                write(m_video_clip_fd, stuffing, 8192);
#endif /*NO USE_STILLPICTURE*/
            }
            close(f);
        } else {
            printf("couldnt open %s\n", filename);
            return -1;
        }
    }
    
    return 0;
}

void finishShowSinglePic()
{
    if (m_video_clip_fd >= 0) {
#ifdef USE_STILLPICTURE

#else /*NO USE_STILLPICTURE*/
#ifdef AZBOX
        if (ioctl(m_video_clip_fd, VIDEO_STOP_STC, 0) < 0)
            printf("VIDEO_STOP_STC failed (%m)\n");
#endif
#endif
        if (ioctl(m_video_clip_fd, VIDEO_STOP, 0) < 0)
            printf("VIDEO_STOP failed (%m)\n");

        if (ioctl(m_video_clip_fd, VIDEO_SELECT_SOURCE, VIDEO_SOURCE_DEMUX) < 0)
            printf("VIDEO_SELECT_SOURCE DEMUX failed (%m)\n");

        close(m_video_clip_fd);
        m_video_clip_fd = -1;
    }
}

