DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
					file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "7c1a32e28b9d145ee25e3d3141dee7821ba8a7e2"
SRCREV_common = "39ac2f563e12d22100e320c95aaab8d8e5812ca9"
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

GST_VERSION_FULL ="1.11.0.1-12"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

do_configure_prepend() {
	${S}/autogen.sh --noconfigure
}

