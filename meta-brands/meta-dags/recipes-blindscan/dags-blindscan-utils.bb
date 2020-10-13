SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "dags-blindscan-dvbs-utils"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_dags-blindscan-dvbs-utils += "virtual/blindscan-dvbs"

SRC_URI = "file://dags_blindscan"

PV = "1.0"
PR = "r0"

S = "${WORKDIR}"

FILES_dags-blindscan-dvbs-utils = "${bindir}/*_blindscan"

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/dags_blindscan" "${D}/${bindir}"
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
