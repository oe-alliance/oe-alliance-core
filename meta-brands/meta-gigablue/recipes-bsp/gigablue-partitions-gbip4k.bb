SUMMARY = "${MACHINEBUILD} partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit deploy

SRCDATE = "20200417"
PR = "${SRCDATE}"

S = "${WORKDIR}/patitions"

SRC_URI = "http://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-partitions-${SRCDATE}.zip"

ALLOW_EMPTY_${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0644 ${S}/fastboot.bin ${D}/usr/share/fastboot.bin
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
}

FILES_${PN} = "/usr/share"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${S}/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "9487fe59c664afdfa0ee954731178f32"
SRC_URI[sha256sum] = "659bea15f1f61c8987481e3889e11f96f70bebf417f84bc31ab8970830858b5f"

INSANE_SKIP_${PN} += "already-stripped"
