SUMMARY = "SF8008 partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20221111"
PR = "${SRCDATE}"

S = "${WORKDIR}/patitions"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-partitions-${SRCDATE}.zip \
  file://flash-apploader \
"

inherit update-rc.d

INITSCRIPT_NAME = "flash-apploader"
INITSCRIPT_PARAMS = "start 90 S ."

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0644 ${S}/fastboot.bin ${D}/usr/share/fastboot.bin
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/flash-apploader ${D}${sysconfdir}/init.d/flash-apploader
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "87a14e1d2173cba303dd78f5134c933e"
SRC_URI[sha256sum] = "3bb0897674647c6d34f47809feb479758625e96eaea82f3e71c7718cb130426a"

INSANE_SKIP:${PN} += "already-stripped"
