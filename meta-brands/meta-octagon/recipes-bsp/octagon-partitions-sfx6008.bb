SUMMARY = "SFX6008 partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20220321"
PR = "${SRCDATE}"

S = "${WORKDIR}/partitions"

SRC_URI = "http://source.mynonpublic.com/octagon/${MACHINE}-partitions-${SRCDATE}.zip \
"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/nand_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "e70ac376b71afcf5cc76cb01d27c7db8"
SRC_URI[sha256sum] = "7d1601a7473e320b16b1b9024c28800ad487d9dae73bb0e6e5df94d26217e435"

INSANE_SKIP:${PN} += "already-stripped"
