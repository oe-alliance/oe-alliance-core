AM_CPPFLAGS = -I$(top_builddir) $(GST_CFLAGS) $(UDEV_CFLAGS)
AM_CFLAGS = -std=gnu11 -Wall -Wextra -Wformat=2

noinst_LTLIBRARIES = libdreamvideo.la
libdreamvideo_la_SOURCES = \
	gstdreamvideosink.c \
	gstdreamvideosink.h \
	dream_video_device.c \
	dream_video_device.h \
	dream_codec.c \
	dream_codec.h \
	dream_h263.c \
	dream_h263.h \
	dream_overlay.c \
	dream_overlay.h

plugin_LTLIBRARIES = libgstdreamvideosink.la
libgstdreamvideosink_la_SOURCES = plugin.c
libgstdreamvideosink_la_LIBADD = libdreamvideo.la $(GST_LIBS) $(UDEV_LIBS)
libgstdreamvideosink_la_LDFLAGS = -module -avoid-version -no-undefined
