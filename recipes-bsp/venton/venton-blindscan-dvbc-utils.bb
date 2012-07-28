DESCRIPTION = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
RDEPENDS = "ncurses"
PACKAGE_ARCH = "${MACHINE_ARCH}"

INIDVBCBIN = "tda1002x"

SRC_URI = "file://${INIDVBCBIN}"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES_${PN} += "virtual/blindscan-dvbc"

RREPLACES_${PN} += "venton-blindscan-utils"
RCONFLICTS_${PN} += "venton-blindscan-utils"

PV = "2.0"
PR = "r2"

S = "${WORKDIR}/"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}
