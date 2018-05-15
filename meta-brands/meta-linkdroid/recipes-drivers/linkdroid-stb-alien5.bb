SUMMARY = "Enigma2 procfs drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

SRCDATE = "20180418"

PV = "${KERNEL_VERSION}+${SRCDATE}"
PR = "r1"

SRC_URI = "file://${MACHINE}-stb-${SRCDATE}.zip"

S = "${WORKDIR}"

inherit module

do_compile() {
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/a5
    install -m 0755 ${WORKDIR}/a5stb.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/a5/
}

do_package_qa() {
}

