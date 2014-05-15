SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"

PACKAGES = "ini-blindscan-dvbs-utils ini-blindscan-dvbs-utils-dbg ini-blindscan-dvbc-utils ini-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_ini-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_ini-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "file://tda1002x file://ini_blindscan"

PV = "2.1"
PR = "r3"

S = "${WORKDIR}/"

FILES_ini-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_ini-blindscan-dvbc-utils = "${bindir}/tda1002x"
FILES_ini-blindscan-dvbs-utils-dbg = "${bindir}/.debug/*_blindscan"
FILES_ini-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x"

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/ini_blindscan" "${D}/${bindir}"
}
