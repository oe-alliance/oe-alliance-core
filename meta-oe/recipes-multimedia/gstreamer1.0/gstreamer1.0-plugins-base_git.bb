DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
					file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "865793273218739656fd9db9b5143ead5d33443c"
SRCREV_common = "76b68df121dfce7c44a25738711c47fb0d034e24"
SRCREV_FORMAT = "base"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-base;branch=master;name=base \
           git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
           file://get-caps-from-src-pad-when-query-caps.patch \
           file://0004-subparse-set-need_segment-after-sink-pad-received-GS.patch \
           file://make-gio_unix_2_0-dependency-configurable.patch \
           file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
           file://0003-riff-media-added-fourcc-to-all-ffmpeg-mpeg4-video-caps.patch \
"

S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.13.1"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

CFLAGS_append += " -Wno-maybe-uninitialized -Wno-uninitialized "

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}
