SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "vuplus-blindscan-dvbs-utils vuplus-blindscan-dvbs-utils-dbg vuplus-blindscan-dvbc-utils vuplus-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_vuplus-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}.tar.bz2"
SRC_URI_arm = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}_arm.tar.bz2;name=arm"

PV = "4.2"
PV_arm = "4.6"
PR = "r10"

S = "${WORKDIR}/blindscan-utils"

INSANE_SKIP_${PN} = "already-stripped"
FILES_vuplus-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils = "${bindir}/tda1002x ${bindir}/ssh108* ${bindir}/tt3l10* ${bindir}/tt2l08* ${bindir}/bcm3148"

FILES_vuplus-blindscan-dvbs-utils-dbg = "${bindir}/.debug/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x ${bindir}/.debug/ssh108* ${bindir}/.debug/tt2l08* ${bindir}/.debug/bcm3148"
FILES_${PN}-dev = "/usr/bin/release.txt"

do_install() {
	install -d ${D}/${bindir}/
	for i in `find ${S} -type f -maxdepth 1`; do
		install -m 0755 $i ${D}/${bindir}/;
	done;
}

SRC_URI[md5sum] = "8e5b2f437b8d3f2b112300ca2e1539d5"
SRC_URI[sha256sum] = "d9b9fcfcafb8f89ded56c2bdd4c7bc6639bfdd93b8c8b78a23a40f4fa6219c2b"

SRC_URI[arm.md5sum] = "f61f4096570ff8fe7ed4d46acc7ba6fb"
SRC_URI[arm.sha256sum] = "1a9e8fd98c8aace692a66e1150db55bad80a5274650d618d3ad91c38ec67dc55"
