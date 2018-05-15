SUMMARY = "AMLogic 905 nftl_dev driver from LE"
SECTION = "base"
PRIORITY = "required"

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

require conf/license/license-gplv2.inc

SRCDATE = "20180513"

PV = "${KERNEL_VERSION}+${SRCDATE}"
PR = "r1"

SRC_URI = "file://${MACHINE}-nftl_dev-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install () {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/extra
    install -m 0644 ${S}/aml_nftl_dev.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/extra/
    install -d ${D}/${sysconfdir}/modules-load.d
    echo aml_nftl_dev > ${D}/${sysconfdir}/modules-load.d/amlnftldev.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/amlnftldev.conf "

do_package_qa() {
}
