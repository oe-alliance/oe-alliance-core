include gstreamer1.0.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
    file://0001-Fix-crash-with-gst-inspect.patch \
    file://0001-Revert-baseparse-fix-GST_BASE_PARSE_FLAG_LOST_SYNC.patch \
"

SRC_URI[md5sum] = "468ae1ce2a82244201fb705fe2954102"
SRC_URI[sha256sum] = "e4f1503c798ba6f0e076f1e44afecd65df00d1bb55b203755717f3e7b38e53d5"

S = "${WORKDIR}/gstreamer-${PV}"

