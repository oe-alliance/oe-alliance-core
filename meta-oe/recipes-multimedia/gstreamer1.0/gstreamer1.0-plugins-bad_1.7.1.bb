include gstreamer1.0-plugins-bad.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://gst/tta/filters.h;beginline=12;endline=29;md5=8a08270656f2f8ad7bb3655b83138e5a \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 \
                    file://gst/tta/crc32.h;beginline=12;endline=29;md5=27db269c575d1e5317fffca2d33b3b50"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-bad;branch=master"
S = "${WORKDIR}/git"
#SRCREV = "${AUTOREV}"
SRCREV = "5b04e77c0ce653150d60f8faa9c6909963b5825c"
inherit gitpkgv
#PV = "1.7+git${SRCPV}"
#PKGV = "1.7+git${GITPKGV}"

SRC_URI += " \
	file://0001-rtmp-fix-seeking-and-potential-segfault.patch \
	file://dvbapi5-fix-old-kernel.patch \
"

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}

RPROVIDES_gstreamer1.0-plugins-bad-hls +=  "gstreamer1.0-plugins-bad-fragmented"
RCONFLICTS_gstreamer1.0-plugins-bad-hls +=  "gstreamer1.0-plugins-bad-fragmented"
RREPLACES_gstreamer1.0-plugins-bad-hls +=  "gstreamer1.0-plugins-bad-fragmented"

TARGET_CFLAGS_append = " -Wno-error=maybe-uninitialized -Wno-error=redundant-decls"