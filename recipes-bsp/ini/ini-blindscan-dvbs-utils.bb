DESCRIPTION = "Utils for DVB-S/S2 blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
RDEPENDS = "ncurses"
PACKAGE_ARCH = "${MACHINE_ARCH}"

INIDVBCBIN = "ini_blindscan"

SRC_URI = "file://${INIDVBCBIN}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

PV = "2.0"
PR = "r4"

S = "${WORKDIR}/"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/ini_blindscan" "${D}/${bindir}"
}
