AM_CPPFLAGS = -I$(top_builddir) -I$(top_srcdir)/src $(GST_CFLAGS) $(GST_CHECK_CFLAGS) $(UDEV_CFLAGS)
AM_CFLAGS = -std=gnu11 -Wall -Wextra -Wformat=2

check_PROGRAMS = test_codec test_device test_sink test_overlay
TESTS = $(check_PROGRAMS)
LDADD = $(top_builddir)/src/libdreamvideo.la $(GST_LIBS) $(GST_CHECK_LIBS) $(UDEV_LIBS)
TESTS_ENVIRONMENT = GST_PLUGIN_PATH_1_0="" GST_PLUGIN_SYSTEM_PATH_1_0="" GST_REGISTRY_1_0="$(abs_builddir)/test-registry.bin" GST_DEBUG_NO_COLOR=1
CLEANFILES = test-registry.bin

test_codec_SOURCES = test_codec.c
test_device_SOURCES = test_device.c
test_sink_SOURCES = test_sink.c
test_overlay_SOURCES = test_overlay.c
