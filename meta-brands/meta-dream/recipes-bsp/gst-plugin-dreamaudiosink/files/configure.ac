AC_INIT([gst-plugin-dreamaudiosink], [0.3.0], [openatv@gmail.com])
AC_CONFIG_MACRO_DIR([m4])
AC_CONFIG_SRCDIR([src/plugin.c])
AC_CONFIG_HEADERS([config.h])

AM_INIT_AUTOMAKE([foreign subdir-objects -Wno-portability])
AM_SILENT_RULES([yes])

AC_PROG_CC
AM_PROG_CC_C_O
LT_INIT([disable-static])

GST_REQUIRED=1.0.0
PKG_CHECK_MODULES(GST, [gstreamer-1.0 >= $GST_REQUIRED gstreamer-base-1.0 >= $GST_REQUIRED])
PKG_CHECK_MODULES(ALSA, [alsa])
PKG_CHECK_MODULES(AV,   [libavcodec libavformat libavutil libswresample])

plugindir="\$(libdir)/gstreamer-1.0"
AC_SUBST(plugindir)

AC_CONFIG_FILES([Makefile src/Makefile])
AC_OUTPUT
