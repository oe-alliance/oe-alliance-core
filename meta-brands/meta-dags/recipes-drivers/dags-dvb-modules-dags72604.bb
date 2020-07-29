SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20200717"
SRCDATE_openspa = "20190327"
KV = "4.1.20"
PV = "${KV}+${SRCDATE}"
PR = "r0"

SRC_URI[md5sum] = "dff8d9800fbc7c978bc6a0c17ce41838"
SRC_URI[sha256sum] = "a31e45fb03ee2bb22eef8908ce06ce5c5cf303e6c0614a37f5bc6b1effd31ee9"
SRC_URI[openspa.md5sum] = "c8c3a1ecf2ff7aa3aa052259ae4c09e8"
SRC_URI[openspa.sha256sum] = "3728da01ddbd85626a332864d0fab92b73cf14e8afc05219ff75bc517754e0c2"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_72604-${KV}-${SRCDATE}.tar.gz"
SRC_URI_openspa = "http://en3homeftp.net/release/images/oedrivers/openspa_bcmlinuxdvb_72604-${KV}-${SRCDATE}.tar.gz;name=openspa"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_compile() {
}

do_populate_sysroot() {
}

do_install() {
    install -d ${D}/lib/modules/${KV}/extra
    cp -Rf ${WORKDIR}/lib/modules/${KV}/extra/*.ko ${D}/lib/modules/${KV}/extra/
}

FILES_${PN} += "/lib/modules/${KV}/extra"
