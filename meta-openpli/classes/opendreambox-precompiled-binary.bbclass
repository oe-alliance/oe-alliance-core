LICENSE = "CLOSED"

SRC_URI += "http://dreamboxupdate.com/download/${DISTRO}/${DISTRO_VERSION}/${PN}/${PN}_${PV}_${PACKAGE_ARCH}.tar.bz2;name=${PACKAGE_ARCH}"

S = "${WORKDIR}/${PN}_${PV}_${PACKAGE_ARCH}"

PACKAGES = "${PN}"

INHIBIT_PACKAGE_STRIP = "1"
