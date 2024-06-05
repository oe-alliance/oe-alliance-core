SUMMARY = "${MACHINE} partitions bootargs files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "https://source.mynonpublic.com/gfutures/${MACHINE}-bootargs-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0755 ${S}/bootargs-8gb.bin ${D}/usr/share/bootargs.bin
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
    install -m 0755 ${S}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}
    install -m 0755 ${S}/${MACHINE}/bootargs-8gb.bin ${DEPLOY_DIR_IMAGE}/gfutures-partitions-${MACHINE}/${MACHINE}
}

addtask deploy before do_package after do_install

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} = "/usr/share"
