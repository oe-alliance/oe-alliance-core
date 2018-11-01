SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "edision-blindscan-dvbs-utils-${MACHINE} edision-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_edision-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"
RPROVIDES_edision-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"

PV = "20180904"

SRC_URI = "http://source.mynonpublic.com/edision/edision-blindscan-utils-${MACHINE}-${PV}.zip"

S = "${WORKDIR}"

FILES_edision-blindscan-dvbs-utils-${MACHINE} = "${bindir}/blindscan"
FILES_edision-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/tda1002x ${D}${bindir}
	install -m 0755 ${S}/blindscan ${D}${bindir}
}

SRC_URI[md5sum] = "f9f98ca43db00590491320168dc7b45f"
SRC_URI[sha256sum] = "f91f1e183f83d0db1bd48665d93d5725751058e2f5502db4990cefd7c7dae4fa"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP_edision-blindscan-dvbs-utils-${MACHINE} = "file-rdeps already-stripped"
INSANE_SKIP_edision-blindscan-dvbc-utils-${MACHINE} = "file-rdeps already-stripped"
