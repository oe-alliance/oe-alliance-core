SUMMARY = "Utilities for transponder & dvb-s/c blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS:${PN} = "ncurses"

COMPATIBLE_MACHINE = "osmini4k"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "edision-blindscan-dvbs-utils-osmini4k edision-blindscan-dvbc-utils-osmini4k"

PV = "20200313"

SRC_URI = "https://source.mynonpublic.com/edision/edision-blindscan-utils-${MACHINE}-${PV}.zip"

S = "${UNPACKDIR}"

FILES:edision-blindscan-dvbs-utils-osmini4k = "${bindir}/blindscan"
FILES:edision-blindscan-dvbc-utils-osmini4k = "${bindir}/tda1002x"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/tda1002x ${D}${bindir}
	install -m 0755 ${S}/blindscan ${D}${bindir}
}

SRC_URI[md5sum] = "f40ff2e821aee8a40eddff481ed17c5e"
SRC_URI[sha256sum] = "1ff13cf2a1fe96c50415a50786f61e78eb340da0095308def031d5ec8e2d3af9"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
INSANE_SKIP:edision-blindscan-dvbs-utils-osmini4k = "file-rdeps already-stripped 32bit-time"
INSANE_SKIP:edision-blindscan-dvbc-utils-osmini4k = "file-rdeps already-stripped 32bit-time"
