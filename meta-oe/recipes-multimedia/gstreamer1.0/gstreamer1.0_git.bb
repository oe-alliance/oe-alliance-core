DEFAULT_PREFERENCE = "-1"

include gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d \
                    "


UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "b22a0c3873926be16313c776ccea764936ab6e4b"
SRCREV_common = "32edeb4f0e665ccad767ab6a104e013522ce7e6f"
SRCREV_FORMAT = "base"

SRC_URI = "git://gitlab.freedesktop.org/gstreamer/gstreamer;protocol=https;branch=master;name=base \
           git://gitlab.freedesktop.org/gstreamer/common;protocol=https;destsuffix=git/common;name=common \
           file://0001-revert-use-new-gst-adapter-get-buffer.patch \
           file://0002-continue-on-nondefined-64bit-atomics.patch \
"

S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.16.0"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

CVE_PRODUCT = "gstreamer"

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}
