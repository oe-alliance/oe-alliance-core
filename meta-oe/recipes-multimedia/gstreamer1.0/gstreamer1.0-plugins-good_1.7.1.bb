include gstreamer1.0-plugins-good.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-plugins-good;branch=master"
SRC_URI += "file://0001-gstrtpmp4gpay-set-dafault-value-for-MPEG4-without-co.patch \
	file://0001-fix-compile-error-261pay.patch \
	file://0001-Revert-qtdemux-respect-qt-segments-in-push-mode-for-.patch \
"

S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"
SRCREV = "5d147467928b47b12439cc1519041a4093357aef"
inherit gitpkgv
PV = "1.7+git${SRCPV}"
PKGV = "1.7+git${GITPKGV}"


CFLAGS_append += " -Wno-maybe-uninitialized -Wno-uninitialized "

do_configure_prepend() {
	cd ${S}
	./autogen.sh --noconfigure
	cd ${B}
}

