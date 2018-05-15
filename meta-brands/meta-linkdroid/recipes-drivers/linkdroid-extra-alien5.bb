SUMMARY = "Enigma2 A5 extra LKMs for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

KV = "3.14.29"
SRCDATE = "20180314"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "file://${MACHINE}-extra-${SRCDATE}.zip"

S = "${WORKDIR}"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/a5/
    install -m 0755 ${WORKDIR}/ampanel.ko ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/a5/
    install -m 0755 ${WORKDIR}/se2io_se.ko ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/a5/
}

do_package_qa() {
}


