require conf/license/license-close.inc

SRC_URI += "https://source.mynonpublic.com/dreambox/${PN}_${PV}_${PACKAGE_ARCH}.tar.bz2;name=${PACKAGE_ARCH}"

S = "${WORKDIR}/${PN}_${PV}_${PACKAGE_ARCH}"

PACKAGES = "${PN}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP:${PN}:append = " already-stripped"
