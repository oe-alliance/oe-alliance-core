SUMMARY = "${MACHINE} parameters files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "http://source.mynonpublic.com/maxytec/${HICHIPSET}-param-${SRCDATE}.zip"

S = "${WORKDIR}"

ALLOW_EMPTY_${PN} = "1"
do_configure[nostamp] = "1"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/pq_param.bin ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${WORKDIR}/${MACHINE}/baseparam.img ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}
}

addtask deploy before do_build after do_install

INSANE_SKIP_${PN} += "already-stripped"