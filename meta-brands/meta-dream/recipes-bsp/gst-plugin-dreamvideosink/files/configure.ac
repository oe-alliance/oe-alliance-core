AC_INIT([gst-plugin-dreamvideosink], [1.0.0], [openatv@gmail.com])
AC_CONFIG_SRCDIR([src/plugin.c])
AC_CONFIG_HEADERS([config.h])

AM_INIT_AUTOMAKE([foreign subdir-objects parallel-tests])
AM_SILENT_RULES([yes])
AC_USE_SYSTEM_EXTENSIONS
AC_PROG_CC
AM_PROG_CC_C_O
LT_INIT([disable-static])
PKG_PROG_PKG_CONFIG

AC_CHECK_HEADERS([linux/dvb/video.h], [],
	[AC_MSG_ERROR([Linux DVB userspace headers are required])])
AC_CHECK_SIZEOF([void *])
AS_IF([test "$ac_cv_sizeof_void_p" != 8],
	[AC_MSG_ERROR([The Dream decoder ABI requires a 64-bit userspace])])

PKG_CHECK_MODULES([GST], [
	gstreamer-1.0 >= 1.14
	gstreamer-base-1.0 >= 1.14
	gstreamer-video-1.0 >= 1.14
	gstreamer-codecparsers-1.0 >= 1.14
	gthread-2.0
])
PKG_CHECK_MODULES([UDEV], [libudev])

AC_ARG_ENABLE([tests],
	[AS_HELP_STRING([--enable-tests], [build hardware-independent regression tests])],
	[], [enable_tests=no])
AS_CASE([$enable_tests], [yes|no], [],
	[AC_MSG_ERROR([--enable-tests accepts yes or no])])
AS_IF([test "x$enable_tests" = xyes], [
	PKG_CHECK_MODULES([GST_CHECK], [gstreamer-check-1.0 >= 1.14])
])
AM_CONDITIONAL([ENABLE_TESTS], [test "x$enable_tests" = xyes])

plugindir='${libdir}/gstreamer-1.0'
AC_SUBST([plugindir])

AC_CONFIG_FILES([Makefile src/Makefile tests/Makefile])
AC_OUTPUT
