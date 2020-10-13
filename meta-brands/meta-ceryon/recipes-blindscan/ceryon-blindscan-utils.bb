SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "ceryon-blindscan-dvbs-utils ceryon-blindscan-dvbc-utils"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_ceryon-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_ceryon-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "file://ceryon_blindscan file://tda1002x"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}"

FILES_ceryon-blindscan-dvbs-utils = "${bindir}/ceryon_blindscan"
FILES_ceryon-blindscan-dvbc-utils = "${bindir}/tda1002x"

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/ceryon_blindscan" "${D}/${bindir}"
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP_ceryon-blindscan-dvbc-utils = "file-rdeps"
INSANE_SKIP_ceryon-blindscan-dvbs-utils = "file-rdeps"
