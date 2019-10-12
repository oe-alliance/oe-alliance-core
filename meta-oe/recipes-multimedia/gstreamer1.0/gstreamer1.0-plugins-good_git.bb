LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe \
"

require gstreamer1.0-common.inc
require gstreamer1.0-plugins-good.inc

SRCREV_FORMAT = "gst_plugins_good"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gst-plugins-good;protocol=https;branch=master;name=gst_plugins_good \
           file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
           file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
"

CFLAGS += "-Wno-maybe-uninitialized -Wno-uninitialized -Wno-incompatible-pointer-types -Wno-deprecated-declarations"

RPROVIDES_${PN}-pulseaudio += "${PN}-pulse"
RPROVIDES_${PN}-soup += "${PN}-souphttpsrc"
