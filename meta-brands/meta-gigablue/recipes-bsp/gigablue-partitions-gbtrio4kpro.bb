SUMMARY = "${MACHINEBUILD} partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit deploy

SRCDATE = "20230110"
PR = "${SRCDATE}"

S = "${WORKDIR}/sources-unpack"
UNPACKDIR = "${S}"

SRC_URI = "https://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-partitions-${SRCDATE}.zip \
  file://flash-apploader \
"

inherit update-rc.d

INITSCRIPT_NAME = "flash-apploader"
INITSCRIPT_PARAMS = "start 90 S ."

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${UNPACKDIR}/patitions/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0644 ${UNPACKDIR}/patitions/fastboot.bin ${D}/usr/share/fastboot.bin
    install -m 0644 ${UNPACKDIR}/patitions/apploader.bin ${D}/usr/share/apploader.bin
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/flash-apploader ${D}${sysconfdir}/init.d/flash-apploader
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-partitions
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "0a57c38738f4ee5a62b97cde592a1a3e"
SRC_URI[sha256sum] = "bff79a66b4ed5d05323308268a7e4513730ebf45e367bcc9e36464b3aa26ca2a"

INSANE_SKIP:${PN} += "already-stripped"
