require conf/license/license-close.inc

SRC_URI += "http://dreamboxupdate.com/download/opendreambox/2.0.0/${PN}/${PN}_${PV}_${PACKAGE_ARCH}.tar.bz2;name=${PACKAGE_ARCH}"

S = "${WORKDIR}/${PN}_${PV}_${PACKAGE_ARCH}"

PACKAGES = "${PN}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

INSANE_SKIP_${PN}_append = " already-stripped"
