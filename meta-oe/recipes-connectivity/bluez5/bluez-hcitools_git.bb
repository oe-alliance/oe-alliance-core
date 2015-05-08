SUMMARY = "bluez-hcitools for Realtek 8723BS  wireless/bluetooth devices"
HOMEPAGE = "http://www.realtek.com/"
LICENSE = "GPLv2"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r1"

CFLAGS_append = " -I${S}  -I${S}/lib "

SRC_URI = "git://github.com/22ktv/bluez-hcitools.git"

S = "${WORKDIR}/git"

inherit pkgconfig

do_install_append_xc7362() {
    install -d ${D}/usr/sbin
    ${STRIP} ${S}/hciattach
    install -m 755 ${S}/hciattach ${D}/usr/sbin/
}


