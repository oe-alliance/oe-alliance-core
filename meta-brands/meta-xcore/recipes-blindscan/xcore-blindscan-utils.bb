SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"

PACKAGES = "xcore-blindscan-dvbs-utils xcore-blindscan-dvbs-utils-dbg"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_xcore-blindscan-dvbs-utils += "virtual/blindscan-dvbs"

SRC_URI = "file://blindscan"

PV = "1.0"
PR = "r2"

S = "${WORKDIR}/"

FILES_xcore-blindscan-dvbs-utils = "${bindir}/blindscan"
FILES_xcore-blindscan-dvbs-utils-dbg = "${bindir}/.debug/blindscan"


do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 "${S}/blindscan" "${D}/${bindir}"
}
