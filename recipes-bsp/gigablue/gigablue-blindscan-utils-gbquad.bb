DESCRIPTION = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

DOWNLOADMACHINE = "gbquad"

PACKAGES = "gigablue-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbc virtual/blindscan-dvbs"
RPROVIDES_gigablue-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"
RPROVIDES_gigablue-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"

SRC_URI = "http://archiv.openmips.com/gigablue-blindscan-utils-${DOWNLOADMACHINE}-${PV}.tar.gz"

PV = "3.3.8"
PR = "r0"

S = "${WORKDIR}/blindscan-utils"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
	install -m 0755 "${S}/gigablue_blindscan" "${D}/${bindir}"
}

FILES_gigablue-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES_gigablue-blindscan-dvbs-utils-${MACHINE} = "${bindir}/gigablue_blindscan"

INHIBIT_PACKAGE_STRIP = "1"

PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI[md5sum] = "a6470f8ae050fa3fe21945f5d69dca3e"
SRC_URI[sha256sum] = "fc567b5905b65ef33148592b3e0a4ba90a56db3cd4aa5518b3ad3bd2942be303"
