SUMMARY = "${MACHINE} partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "https://source.mynonpublic.com/gfutures/${MACHINE}-partitions-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0755 ${S}/fastboot.bin ${D}/usr/share/fastboot.bin
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/fastboot.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}
    install -m 0755 ${S}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/${MACHINE}/logo.img ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/${MACHINE}/dtbo.img ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
}

addtask deploy before do_package after do_install

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} = "/usr/share"
