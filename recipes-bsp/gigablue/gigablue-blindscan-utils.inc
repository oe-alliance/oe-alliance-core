DESCRIPTION = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PACKAGES = "gigablue-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archiv.openmips.com/gigablue-blindscan-utils-${DOWNLOADMACHINE}-${PV}.tar.gz"

PV = "3.1.1"
PR = "r1"

S = "${WORKDIR}/blindscan-utils"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

FILES_gigablue-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"

INHIBIT_PACKAGE_STRIP = "1"

PACKAGE_ARCH := "${MACHINE_ARCH}"
