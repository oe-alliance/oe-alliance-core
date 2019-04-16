DEFAULT_PREFERENCE = "-1"

include gstreamer1.0-plugins-bad.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 \
"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_base = "8388a031ab4acdd7590ba14add07dd52ab3ac3ae"
SRCREV_common = "32edeb4f0e665ccad767ab6a104e013522ce7e6f"
SRCREV_FORMAT = "base"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-bad;branch=master;name=base \
           git://anongit.freedesktop.org/gstreamer/common;destsuffix=git/common;name=common \
"

SRC_URI += " \
			file://configure-allow-to-disable-libssh2.patch \
			file://fix-maybe-uninitialized-warnings-when-compiling-with-Os.patch \
			file://avoid-including-sys-poll.h-directly.patch \
			file://ensure-valid-sentinels-for-gst_structure_get-etc.patch \
			file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
			\
			file://0001-rtmp-fix-seeking-and-potential-segfault.patch \
			file://0004-rtmp-hls-tsdemux-fix.patch \
			file://dvbapi5-fix-old-kernel.patch \
			file://hls-main-thread-block.patch \
			"
S = "${WORKDIR}/git"

GST_VERSION_FULL ="1.15.90"
inherit gitpkgv
PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

EXTRA_OECONF += " \
    --disable-openjpeg \
    "

TARGET_CFLAGS_append = " -Wno-error=maybe-uninitialized -Wno-error=redundant-decls -Wno-error=deprecated-declarations"

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}
