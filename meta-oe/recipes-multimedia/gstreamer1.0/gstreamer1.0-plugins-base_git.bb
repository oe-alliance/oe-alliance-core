DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
					file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "b728e91ebb4452f7a8d3aa1da4f00565bce09092"
SRCREV_common = "93ae13f2c3c58a4c2b7c111817b720a272d504d7"
SRCREV_FORMAT = "base"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-base;branch=master;name=base \
           git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRC_URI_append = "\
	file://0001-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
    file://0002-Makefile.am-prefix-calls-to-pkg-config-with-PKG_CONF.patch \
    file://0003-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-caps.patch \
    file://0004-rtsp-drop-incorrect-reference-to-gstreamer-sdp-in-Ma.patch \
"

S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.11.1"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

do_configure_prepend() {
	${S}/autogen.sh --noconfigure
}

