LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 \
"

require gstreamer1.0-common.inc
require gstreamer1.0-plugins-bad.inc

SRCREV_FORMAT = "gst_plugins_bad"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-plugins-bad;protocol=https;branch=master;name=gst_plugins_bad"

SRC_URI += " \
        file://configure-allow-to-disable-libssh2.patch \
        file://fix-maybe-uninitialized-warnings-when-compiling-with-Os.patch \
        file://avoid-including-sys-poll.h-directly.patch \
        file://ensure-valid-sentinels-for-gst_structure_get-etc.patch \
        file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
        \
        file://0001-rtmp-fix-seeking-and-potential-segfault.patch \
        file://0004-rtmp-hls-tsdemux-fix.patch \
        file://dvbapi5-fix-old-kernel.patch \
        file://hls-main-thread-block.patch \
        "

EXTRA_OECONF += "--disable-openjpeg"

TARGET_CFLAGS_append = " -Wno-error=maybe-uninitialized -Wno-error=redundant-decls -Wno-error=deprecated-declarations"
