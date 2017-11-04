SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "vuplus-blindscan-dvbs-utils vuplus-blindscan-dvbc-utils"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_vuplus-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}.tar.bz2"
SRC_URI_arm = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}_arm.tar.bz2;name=arm"

PV = "4.3"
PV_arm = "4.8"
PR = "r11"

S = "${WORKDIR}/blindscan-utils"

FILES_vuplus-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils = "${bindir}/tda1002x ${bindir}/ssh108* ${bindir}/tt3l10* ${bindir}/tt2l08* ${bindir}/bcm3148"

FILES_${PN}-dev = "/usr/bin/release.txt"

do_install() {
	install -d ${D}/${bindir}/
	for i in `find ${S} -type f -maxdepth 1`; do
		install -m 0755 $i ${D}/${bindir}/;
	done;
}

SRC_URI[md5sum] = "a8ca2f8ce06d37b7d01b729b1e4e4abb"
SRC_URI[sha256sum] = "ec9b5dd552e72a0d775a77212350b71f5ea6f3619687c40c2bf97b12c5d7abd9"

SRC_URI[arm.md5sum] = "7dea1e6dd1a9e53ef5c29750cf834d96"
SRC_URI[arm.sha256sum] = "e35a4ef006ae66b79c224f3f6c5364a402bf4512fc39d3df756ca22a88d4ab0b"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
