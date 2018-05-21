SUMMARY = "Airdigital partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Airdigital"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

SRCDATE = "20180521"
PR = "${SRCDATE}"

S = "${WORKDIR}"

SRC_URI = "	http://source.mynonpublic.com/zgemma/${MACHINE}-partitions_${SRCDATE}.zip"

ALLOW_EMPTY_${PN} = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${WORKDIR}/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0644 ${WORKDIR}/fastboot.bin ${D}/usr/share/fastboot.bin
}

FILES_${PN} = "/usr/share"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
    install -m 0755 ${WORKDIR}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${WORKDIR}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${WORKDIR}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions/${MACHINE}
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "5f466ee171294707bb08af94afdf886a"
SRC_URI[sha256sum] = "d5ab832126b7f57f2256240a67044d4b411ea86464355ca0dc5e0d05f6a5a637"

INSANE_SKIP_${PN} += "already-stripped"
