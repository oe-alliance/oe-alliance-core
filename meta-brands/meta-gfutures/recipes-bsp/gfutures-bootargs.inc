SUMMARY = "${MACHINE} partitions bootargs files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "http://source.mynonpublic.com/gfutures/${MACHINE}-bootargs-${SRCDATE}.zip"

S = "${WORKDIR}"

ALLOW_EMPTY_${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0755 ${WORKDIR}/bootargs-8gb.bin ${D}/usr/share/bootargs.bin
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${WORKDIR}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
}

addtask deploy before do_build after do_install

INSANE_SKIP_${PN} += "already-stripped"

FILES_${PN} = "/usr/share"
