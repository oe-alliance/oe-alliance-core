DEFAULT_PREFERENCE = "-1"

include gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
    git://anongit.freedesktop.org/gstreamer/gstreamer;branch=master;name=master \
    git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRC_URI += " \
    file://0001-Fix-crash-with-gst-inspectv2.patch \
    file://0001-revert-use-new-gst-adapter-get-buffer.patch \
"

S = "${WORKDIR}/git"

SRCREV_master = "529406598582b0931ff6fd081e0a7435a7480eec"
SRCREV_common = "1b39f6d85a3d51ac6d1b44d8c821fd9b76b34454"
SRCREV_FORMAT = "master"
inherit gitpkgv

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}

# The option to configure tracer hooks was added prior to the 1.7.2 release
# https://cgit.freedesktop.org/gstreamer/gstreamer/commit/?id=e5ca47236e4df4683707f0bcf99181a937d358d5
PACKAGECONFIG[gst-tracer-hooks] = "--enable-gst-tracer-hooks,--disable-gst-tracer-hooks,"
PACKAGECONFIG[trace-historic] = "--enable-trace,--disable-trace,"