LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d \
                    "

require gstreamer1.0-common.inc
require gstreamer1.0.inc

SRCREV_FORMAT = "gst"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gstreamer;protocol=https;branch=master;name=gst \
           file://0001-revert-use-new-gst-adapter-get-buffer.patch \
           file://0002-continue-on-nondefined-64bit-atomics.patch \
"

CVE_PRODUCT = "gstreamer"
