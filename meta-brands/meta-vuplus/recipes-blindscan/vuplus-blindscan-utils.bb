SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS:${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "vuplus-blindscan-dvbs-utils vuplus-blindscan-dvbc-utils \
            vuplus-blindscan-dvbs-utils-doc vuplus-blindscan-dvbc-utils-doc"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES:vuplus-blindscan-dvbs-utils += "virtual-blindscan-dvbs"
RPROVIDES:vuplus-blindscan-dvbc-utils += "virtual-blindscan-dvbc"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/utils/vuplus-blindscan-utils-${PV}.tar.bz2"
SRC_URI:arm = "https://source.mynonpublic.com/vuplus/release/utils/vuplus-blindscan-utils-${PV}_arm.tar.bz2;name=arm"

PV = "4.4"
PV:arm = "5.1"
PR = "r11"

S = "${WORKDIR}/blindscan-utils"

FILES:vuplus-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES:vuplus-blindscan-dvbc-utils = "${bindir}/tda1002x ${bindir}/ssh108* ${bindir}/tt3l10* ${bindir}/tt2l08* ${bindir}/bcm3*"
FILES:vuplus-blindscan-dvbs-utils-doc = "${datadir}/vuplus-blindscan-utils-release.txt"
FILES:vuplus-blindscan-dvbc-utils-doc = "${datadir}/vuplus-blindscan-utils-release.txt"

do_install() {
	install -d ${D}${bindir}
	for i in `find ${S} -type f -maxdepth 1`; do
		install -m 0755 $i ${D}${bindir};
	done;
	install -d ${D}${datadir}
	mv ${D}${bindir}/release.txt ${D}${datadir}/vuplus-blindscan-utils-release.txt
}

SRC_URI[md5sum] = "b6d485516762453dcb829d914b4e06b3"
SRC_URI[sha256sum] = "cb7045db4aee0d1ef9392231d2bfc7f843e9e9ec82ceb17d86dd7c1fe56bef4b"

SRC_URI[arm.md5sum] = "1744184485bdc6889f6556504e574c6a"
SRC_URI[arm.sha256sum] = "518775bf3ff43ade300741232ce4d303d4aeaea09f1a7d7256e40a4fe95156ca"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP:vuplus-blindscan-dvbs-utils = "ldflags"
INSANE_SKIP:vuplus-blindscan-dvbc-utils = "ldflags"
