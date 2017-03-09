DEFAULT_PREFERENCE = "-1"

include gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d \
                    "


UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "08ee7a86edfe4fccb0b254d3541bb59f0ada071c"
SRCREV_common = "93ae13f2c3c58a4c2b7c111817b720a272d504d7"
SRCREV_FORMAT = "base"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gstreamer;branch=master;name=base \
		   git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRC_URI += "file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
			file://0001-revert-use-new-gst-adapter-get-buffer.patch"

S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.11.2.1-01"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GITPKGVTAG}"


do_configure_prepend() {
	${S}/autogen.sh --noconfigure
}

