DESCRIPTION = "Utils for DVB-S/S2 blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
RDEPENDS = "ncurses"
PACKAGE_ARCH = "${MACHINE_ARCH}"

INIDVBCBIN = "venton_blindscan"

SRC_URI = "file://${INIDVBCBIN}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

RREPLACES_${PN} += "venton-blindscan-utils"
RCONFLICTS_${PN} += "venton-blindscan-utils"

PV = "2.0"
PR = "r2"

S = "${WORKDIR}/"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/venton_blindscan" "${D}/${bindir}"
}
