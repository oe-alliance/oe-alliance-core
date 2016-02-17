include gstreamer1.0.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
    file://0001-Fix-crash-with-gst-inspect.patch \
    file://0001-Revert-baseparse-fix-GST_BASE_PARSE_FLAG_LOST_SYNC.patch \
"

SRC_URI[md5sum] = "20dabb7d084f83691d258248bcb0b28b"
SRC_URI[sha256sum] = "96c884e30c4aa7d8ecb6136d467544bec9af36c49dc5f351c3d2ddd23d456c97"

S = "${WORKDIR}/gstreamer-${PV}"

