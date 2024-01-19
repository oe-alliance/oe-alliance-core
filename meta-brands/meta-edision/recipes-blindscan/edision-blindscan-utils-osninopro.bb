SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS:${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "edision-blindscan-dvbs-utils-${MACHINE} edision-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES:edision-blindscan-dvbs-utils-${MACHINE} += "virtual-blindscan-dvbs"
RPROVIDES:edision-blindscan-dvbc-utils-${MACHINE} += "virtual-blindscan-dvbc"

PV = "20190805"

SRC_URI = "https://source.mynonpublic.com/edision/edision-blindscan-utils-${MACHINE}-${PV}.zip"

S = "${WORKDIR}"

FILES:edision-blindscan-dvbs-utils-${MACHINE} = "${bindir}/blindscan"
FILES:edision-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/tda1002x ${D}${bindir}
	install -m 0755 ${S}/blindscan ${D}${bindir}
}

SRC_URI[md5sum] = "3bec04811dac24f181cfeaf412af9a44"
SRC_URI[sha256sum] = "5715eb813ab203859955491678b982d858815861f15291840bba01379628726d"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP:edision-blindscan-dvbs-utils-${MACHINE} = "file-rdeps already-stripped"
INSANE_SKIP:edision-blindscan-dvbc-utils-${MACHINE} = "file-rdeps already-stripped"
