SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "xcore-blindscan-dvbs-utils xcore-blindscan-dvbs-utils-dbg xcore-blindscan-dvbc-utils xcore-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_xcore-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_xcore-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "file://blindscan file://tda1002x"

PV = "1.0"
PR = "r6"

S = "${WORKDIR}/"

FILES_xcore-blindscan-dvbs-utils = "${bindir}/blindscan"
FILES_xcore-blindscan-dvbc-utils = "${bindir}/tda1002x"
FILES_xcore-blindscan-dvbs-utils-dbg = "${bindir}/.debug/blindscan"
FILES_xcore-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x"


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/blindscan" "${D}/${bindir}"
}

INHIBIT_PACKAGE_STRIP = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

