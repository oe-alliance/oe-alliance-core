SUMMARY = "Azbox compatibility links"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PV = "1.0"
PR = "r6"

do_install() {
    install -d ${D}/lib
    ln -sf libgcc_s.so.1 ${D}/lib/libgcc_s_nof.so.1
    install -d ${D}/usr/lib
    ln -sf libpython2.7.so.1.0 ${D}/usr/lib/libpython2.6.so.1.0
    ln -sf libgif.so.7 ${D}/usr/lib/libungif.so.4
}

PACKAGES = "${PN}"
