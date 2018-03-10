SUMMARY = "Airdigital partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "Airdigital"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc
inherit deploy

SRCDATE = "20180219"
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

SRC_URI[md5sum] = "8eacdc923f6031aa3886eb9a5fe1e15a"
SRC_URI[sha256sum] = "f7b730d2c16bc80560089ed410c6b5ce76a336041c92e467f994828d8d5e697b"

INSANE_SKIP_${PN} += "already-stripped"
