SUMMARY = "Enigma2 procfs drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

KV = "3.14.29"
SRCDATE = "20180712"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "file://${MACHINE}-stb-${SRCDATE}.zip"

S = "${WORKDIR}"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/a5
    install -m 0755 ${WORKDIR}/a5stb.ko ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/a5/
}

do_package_qa() {
}

