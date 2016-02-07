include gstreamer1.0.inc
require mips-only.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
    file://0001-Fix-crash-with-gst-inspect.patch \
"

SRC_URI[md5sum] = "201c15ac4b956833f7f6774485433969"
SRC_URI[sha256sum] = "52ef885647afef11c8b7645a9afefe04aa09e8971c4b932e7717872ab8a30fcc"

S = "${WORKDIR}/gstreamer-${PV}"