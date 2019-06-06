LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

require gstreamer1.0-common.inc
require gstreamer1.0-plugins-base.inc

SRCREV_FORMAT = "gst_plugins_base"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-plugins-base;protocol=https;branch=master;name=gst_plugins_base \
           file://get-caps-from-src-pad-when-query-caps.patch \
           file://0004-subparse-set-need_segment-after-sink-pad-received-GS.patch \
           file://make-gio_unix_2_0-dependency-configurable.patch \
           file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
           file://0003-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-caps.patch \
"

CFLAGS += "-Wno-maybe-uninitialized -Wno-uninitialized"
