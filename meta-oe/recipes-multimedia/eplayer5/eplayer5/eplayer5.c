/*
 * eplayer3: command line playback using libeplayer3
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <linux/fb.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>

#include <gst/gst.h>
#include <gst/pbutils/missing-plugins.h>

typedef enum
{
	GST_PLAY_FLAG_VIDEO         = (1 << 0),
	GST_PLAY_FLAG_AUDIO         = (1 << 1),
	GST_PLAY_FLAG_TEXT          = (1 << 2),
	GST_PLAY_FLAG_VIS           = (1 << 3),
	GST_PLAY_FLAG_SOFT_VOLUME   = (1 << 4),
	GST_PLAY_FLAG_NATIVE_AUDIO  = (1 << 5),
	GST_PLAY_FLAG_NATIVE_VIDEO  = (1 << 6),
	GST_PLAY_FLAG_DOWNLOAD      = (1 << 7),
	GST_PLAY_FLAG_BUFFERING     = (1 << 8),
	GST_PLAY_FLAG_DEINTERLACE   = (1 << 9),
	GST_PLAY_FLAG_SOFT_COLORBALANCE = (1 << 10),
	GST_PLAY_FLAG_FORCE_FILTERS = (1 << 11),
} GstPlayFlags;


int kbhit(void) {
    struct timeval tv;
    fd_set read_fd;

    tv.tv_sec=1;
    tv.tv_usec=0;

    FD_ZERO(&read_fd);
    FD_SET(0,&read_fd);

    if(select(1, &read_fd, NULL, NULL, &tv) == -1)
            return 0;

    if(FD_ISSET(0,&read_fd))
            return 1;

    return 0;
}

int main(int argc,char* argv[]) {
    int showInfos = 0, noinput = 0;
    char file[255] = {""};
    int speedmap = 0;
    gdouble speed = 1.0;
    printf("%s >\n", __FILE__);

    if (argc < 2)
    {
        printf("give me a filename please\n");
        exit(1);
    }

    strcat(file, argv[1]);

    if(argc == 3 && !strcmp(argv[2], "-n"))
      noinput = 1;

    printf ("File=%s\n", file);

    gst_init(&argc, &argv);

    GstElement *m_gst_playbin;
    gchar *uri;
    uri = g_filename_to_uri(file, NULL, NULL);
    m_gst_playbin = gst_element_factory_make("playbin", "playbin");

    printf ("URI=%s\n", uri);

    g_object_set (G_OBJECT (m_gst_playbin), "uri", uri, NULL);
    guint flags = GST_PLAY_FLAG_AUDIO | GST_PLAY_FLAG_VIDEO | GST_PLAY_FLAG_TEXT | GST_PLAY_FLAG_NATIVE_VIDEO | GST_PLAY_FLAG_BUFFERING;
    g_object_set (G_OBJECT (m_gst_playbin), "flags", flags, NULL);
    g_free(uri);

    //GstElement *subsink = gst_element_factory_make("appsink", "subtitle_sink");

    //gst_bus_set_sync_handler(gst_pipeline_get_bus (GST_PIPELINE (m_gst_playbin)), gstBusSyncHandler, this);

    gst_element_set_state (m_gst_playbin, GST_STATE_PLAYING);

        while(1) {
            int Key = 0;

            if(kbhit())
                if(noinput == 0)
            Key = getchar();
            
            if(Key == 0 || Key == 0xA)
                continue;
            
            switch (Key) {
            case 'q': //STOP
                gst_element_set_state(m_gst_playbin, GST_STATE_NULL);
                break;

            case 'c': //CONTINUE
                gst_element_set_state (m_gst_playbin, GST_STATE_PLAYING);
                
                speed = 1.0;
                printf("Continue with speed %f\n", speed);
                gst_element_seek (m_gst_playbin, speed, GST_FORMAT_TIME, GST_SEEK_FLAG_NONE, 
                    GST_SEEK_TYPE_NONE, 0, 
                    GST_SEEK_TYPE_NONE, -1);
                
                break;

            case 'p': //PAUSE
                gst_element_set_state(m_gst_playbin, GST_STATE_PAUSED);
                break;

            case 'k': {
                int Key2 = getchar() - 48;
                double sec=0.0;

                switch (Key2) {
                    case 1: sec=-15.0;break;
                    case 4: sec=-60.0;break;
                    case 7: sec=-300.0;break;
                    case 3: sec= 15.0;break;
                    case 6: sec= 60.0;break;
                    case 9: sec= 300.0;break;
                }

                printf("seconds %d \n", Key2);
                gint64 time_nanoseconds;
                gint64 pos;
                GstFormat fmt = GST_FORMAT_TIME;
                gst_element_query_position(m_gst_playbin, fmt, pos);

                time_nanoseconds = pos + (sec * 1000000000);
                if (time_nanoseconds < 0) time_nanoseconds = 0;


                double seekTo = 0;
                seekTo = time_nanoseconds / 1000000000.0;
                printf("SeekTo = %02d:%02d:%02d (%.4f sec)\n", (int)((seekTo/60)/60)%60, (int)(seekTo/60)%60, (int)seekTo%60, seekTo);

                gst_element_seek (m_gst_playbin, 1.0, GST_FORMAT_TIME, GST_SEEK_FLAG_FLUSH,
                    GST_SEEK_TYPE_SET, time_nanoseconds,
                    GST_SEEK_TYPE_NONE, GST_CLOCK_TIME_NONE);
                break;
            }

            case 'l': {
                double length = 0;
                GstFormat fmt = GST_FORMAT_TIME; //Returns time in nanosecs
                gint64 len;
                gst_element_query_duration(m_gst_playbin, fmt, len);

                length = len / 1000000000.0;
                printf("Length = %02d:%02d:%02d (%.4f sec)\n", (int)((length/60)/60)%60, (int)(length/60)%60, (int)length%60, length);
                break;
            }
            case 'j': {
                double sec = 0;
                GstFormat fmt = GST_FORMAT_TIME; //Returns time in nanosecs
                gint64 pos;
                gst_element_query_position(m_gst_playbin, fmt, pos);

                sec = pos / 1000000000.0;
                printf("Pts = %02d:%02d:%02d (%.4f sec)\n", (int)((sec/60)/60)%60, (int)(sec/60)%60, (int)sec%60, sec);
                break;
            }

            case 'f':
            {
                if (speed < 1.0)
                    speed = 1.0;
                    
                speed++;

                if (speed > 4.0)
                    speed = 1.0;
                
                printf("FastForward with speed %f\n", speed);
                gst_element_seek (m_gst_playbin, speed, GST_FORMAT_TIME, GST_SEEK_FLAG_NONE, 
                    GST_SEEK_TYPE_NONE, 0, 
                    GST_SEEK_TYPE_NONE, -1);
                
                break;
            }

             case 'b':
            {
                if (speed >= 1.0)
                    speed = 0.0;
                   
                speed--;
                   
                if (speed < -4.0)
                    speed = -1.0;
                
                printf("Reverse with speed %f\n", speed);
                gst_element_seek (m_gst_playbin, speed, GST_FORMAT_TIME, GST_SEEK_FLAG_NONE, 
                    GST_SEEK_TYPE_NONE, 0, 
                    GST_SEEK_TYPE_NONE, -1);
                
                break;
            }

            case 'i':
            {
                break;
            }

            default:
            {
                printf("Control: %x\n", Key);
                printf("al:       List audio tracks\n");
                printf("ac:       List current audio track\n");
                printf("a[id]     Select audio track\n");
                printf("sl:       List subtitles\n");
                printf("sc:       List current subtitle\n");
                printf("s[id]     Select subtitles\n");
                printf("q:        Stop\n");
                printf("c:        Continue\n");
                printf("p:        Pause\n");
                printf("f:        Increase speed (Fast forward) (stepwise)\n");
                printf("b:        Decrease speed (Fast reverse) (stepwise)\n");
                printf("l:        Print duration\n");
                printf("j:        Print current PTS\n");
                printf("k[1,4,7]: Jump back [15,60,300] seconds\n");
                printf("k[3,6,9]: Jump forward [15,60,300] seconds\n");
                printf("i:        Print Info\n");
                break;
            }
        }
    }

    //printOutputCapabilities();

    exit(0);
}
