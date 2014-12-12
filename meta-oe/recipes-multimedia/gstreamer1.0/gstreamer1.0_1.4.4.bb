include gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    http://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
    file://0001-Fix-crash-with-gst-inspect.patch \
"

SRC_URI[md5sum] = "98f4a6d45a28dd195144baef0244ba38"
SRC_URI[sha256sum] = "f0e305d91a93d05bf9e332cd4256ca07d77f5186a4d73847b7ae6db218f2c237"
S = "${WORKDIR}/gstreamer-${PV}"

