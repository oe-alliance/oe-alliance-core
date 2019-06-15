LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://tests/check/elements/xingmux.c;beginline=1;endline=21;md5=4c771b8af188724855cb99cadd390068 \
"

require gstreamer1.0-common.inc
require gstreamer1.0-plugins-ugly.inc

SRCREV_FORMAT = "gst_plugins_ugly"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-plugins-ugly;protocol=https;branch=master;name=gst_plugins_ugly \
           file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
"
