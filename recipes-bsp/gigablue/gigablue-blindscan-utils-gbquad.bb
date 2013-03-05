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

PV = "3.5.1"
PR = "r1"

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

SRC_URI[md5sum] = "671e1e0a4f031e33e87f4bb06c3c73a3"
SRC_URI[sha256sum] = "543d346d5d87816832c8d71a0e017ad11bacc136be17f5dd52cde828f4436afb"
