SUMMARY = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "4.0"

PR = "r13"

S = "${UNPACKDIR}/blindscan-utils"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/gigablue_blindscan" "${D}/${bindir}"
}

FILES:gigablue-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES:gigablue-blindscan-dvbs-utils-${MACHINE} = "${bindir}/gigablue_blindscan"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time ldflags"
