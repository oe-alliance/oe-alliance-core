SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "ini-blindscan-dvbs-utils ini-blindscan-dvbc-utils"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_ini-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_ini-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "file://tda1002x file://ini_blindscan"

PV = "2.1"
PR = "r4"

S = "${WORKDIR}"

FILES_ini-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_ini-blindscan-dvbc-utils = "${bindir}/tda1002x"

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/ini_blindscan" "${D}/${bindir}"
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
