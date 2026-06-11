plugin_LTLIBRARIES = libgstdreamaudiosink.la

libgstdreamaudiosink_la_SOURCES = \
    plugin.c \
    gstdreamaudiosink.c \
    gstdreamaudiosink.h \
    dream_decoder.c \
    dream_decoder.h \
    dream_alsa.c \
    dream_alsa.h \
    dream_log.h \
    dream_avsync.c \
    dream_avsync.h

libgstdreamaudiosink_la_CFLAGS  = $(GST_CFLAGS) $(ALSA_CFLAGS) $(AV_CFLAGS)
libgstdreamaudiosink_la_LIBADD  = $(GST_LIBS)   $(ALSA_LIBS)   $(AV_LIBS)
libgstdreamaudiosink_la_LDFLAGS = -module -avoid-version
