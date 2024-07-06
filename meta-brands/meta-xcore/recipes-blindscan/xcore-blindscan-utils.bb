SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS:${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "xcore-blindscan-dvbs-utils xcore-blindscan-dvbc-utils"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES:xcore-blindscan-dvbs-utils += "virtual-blindscan-dvbs"
RPROVIDES:xcore-blindscan-dvbc-utils += "virtual-blindscan-dvbc"

SRC_URI = "file://blindscan file://tda1002x"

PV = "1.0"
PR = "r7"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

FILES:xcore-blindscan-dvbs-utils = "${bindir}/blindscan"
FILES:xcore-blindscan-dvbc-utils = "${bindir}/tda1002x"

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/blindscan" "${D}/${bindir}"
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time"
