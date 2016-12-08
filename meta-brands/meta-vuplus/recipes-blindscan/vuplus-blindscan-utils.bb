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

SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}.tar.gz"
SRC_URI_arm = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}_arm.tar.gz;name=arm"

PV = "4.2"
PV_arm = "4.5"
PR = "r8"

S = "${WORKDIR}/blindscan-utils"

INSANE_SKIP_${PN} = "already-stripped"
FILES_vuplus-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils = "${bindir}/tda1002x ${bindir}/ssh108* ${bindir}/tt3l10* ${bindir}/tt2l08* ${bindir}/bcm3148"

FILES_vuplus-blindscan-dvbs-utils-dbg = "${bindir}/.debug/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x ${bindir}/.debug/ssh108* ${bindir}/.debug/tt2l08* ${bindir}/.debug/bcm3148"

do_install() {
	install -d ${D}/${bindir}/
	for i in `find ${S} -type f -maxdepth 1`; do
		install -m 0755 $i ${D}/${bindir}/;
	done;
}

SRC_URI[md5sum] = "93f74f70612b209641b5673ad7c523dc"
SRC_URI[sha256sum] = "cb3af3cdecedae47f0511a4997124c897e43aaf8126609fa654447637b8a58f4"

SRC_URI[arm.md5sum] = "d824892dc728a667e88eb8f6d7c877ec"
SRC_URI[arm.sha256sum] = "5b35e3edfeb217beb7bca04156273218fa98b50536ffb3993a94904a95bcf90a"
