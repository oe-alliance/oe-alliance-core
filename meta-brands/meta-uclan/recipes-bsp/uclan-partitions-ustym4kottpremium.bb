SUMMARY = "ustym4kottpremium partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20220113"
PR = "${SRCDATE}"

S = "${WORKDIR}/partitions"

SRC_URI = "https://source.mynonpublic.com/uclan/${MACHINE}-partitions-${SRCDATE}.zip \
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
    install -m 0644 ${S}/sbl.bin.sig ${D}/usr/share/sbl.bin.sig
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/flash-apploader ${D}${sysconfdir}/init.d/flash-apploader
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/fbl.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/trustedcore.img.sig.enc ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/sbl.bin.sig ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "0e0a3487f287747f1cba1ab008afc0f5"
SRC_URI[sha256sum] = "cf53f9d2e9d0f115568b9ced7a8629ce45680f600f1e8383a57ba647da71890e"

INSANE_SKIP:${PN} += "already-stripped"
