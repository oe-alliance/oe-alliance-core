SUMMARY = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

PV = "4.0"
PR = "r12"

PACKAGES = "gigablue-blindscan-dvbs-utils-mips-${MACHINE} gigablue-blindscan-dvbc-utils-mips-${MACHINE} gigablue-blindscan-dvbs-utils-mips-${MACHINE}-dbg gigablue-blindscan-dvbc-utils-mips-${MACHINE}-dbg"

PROVIDES += "virtual/blindscan-dvbc virtual/blindscan-dvbs"
RPROVIDES_gigablue-blindscan-dvbc-utils-mips-${MACHINE} += "virtual/blindscan-dvbc"
RPROVIDES_gigablue-blindscan-dvbs-utils-mips-${MACHINE} += "virtual/blindscan-dvbs"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://archiv.openmips.com/gigablue-blindscan-utils-${SRCDATE}.tgz"

S = "${WORKDIR}/blindscan-utils"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/gigablue_blindscan" "${D}/${bindir}"
}

FILES_gigablue-blindscan-dvbc-utils-mips-${MACHINE} = "${bindir}/tda1002x"
FILES_gigablue-blindscan-dvbs-utils-mips-${MACHINE} = "${bindir}/gigablue_blindscan"
FILES_gigablue-blindscan-dvbc-utils-mips-${MACHINE}-dbg = "${bindir}/.debug/*_blindscan"
FILES_gigablue-blindscan-dvbs-utils-mips-${MACHINE}-dbg = "${bindir}/.debug/tda1002x"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
