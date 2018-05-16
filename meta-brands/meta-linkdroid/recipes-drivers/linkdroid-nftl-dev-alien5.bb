SUMMARY = "AMLogic 905 nftl_dev driver from LE"
SECTION = "base"
PRIORITY = "required"

DEPENDS = "virtual/${TARGET_PREFIX}gcc"

require conf/license/license-gplv2.inc

KV = "3.14.29"
SRCDATE = "20180513"

PV = "${KV}+${SRCDATE}"
PR = "r1"

SRC_URI = "file://${MACHINE}-nftl_dev-${SRCDATE}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/extra
    install -m 0644 ${S}/aml_nftl_dev.ko ${D}${nonarch_base_libdir}/modules/${KV}/kernel/drivers/extra/
    install -d ${D}/${sysconfdir}/modules-load.d
    echo aml_nftl_dev > ${D}/${sysconfdir}/modules-load.d/amlnftldev.conf
}

FILES_${PN} += "${sysconfdir}/modules-load.d/amlnftldev.conf "

do_package_qa() {
}
