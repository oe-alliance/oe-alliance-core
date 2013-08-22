DESCRIPTION = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

LICENSE = "CLOSED"

PACKAGES = "vuplus-blindscan-dvbs-utils-${MACHINE} vuplus-blindscan-dvbc-utils-${MACHINE} vuplus-blindscan-dvbs-utils-${MACHINE}-dbg"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"
RPROVIDES_vuplus-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${DOWNLOADMACHINE}-${PV}.tar.bz2"

PV = "3.2"
PR = "r7"
PACKAGE_ARCH := "${MACHINE_ARCH}"

S = "${WORKDIR}/blindscan-utils"

FILES_vuplus-blindscan-dvbs-utils-${MACHINE} = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES_vuplus-blindscan-dvbs-utils-${MACHINE}-dbg = "/usr/bin/.debug"

do_install() {
    install -d ${D}/${bindir}/
    for i in `find ${S} -type f -maxdepth 1`; do
        install -m 0755 $i ${D}/${bindir}/;
    done;
}
