SUMMARY = "${MACHINE} fastboot files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "https://source.mynonpublic.com/maxytec/${MACHINE}-fastboot-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/${MACHINE}/fastboot.bin ${DEPLOY_DIR_IMAGE}/maxytec-partitions-${MACHINE}/${MACHINE}
}

addtask deploy before do_package after do_install

INSANE_SKIP:${PN} += "already-stripped"
