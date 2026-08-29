SUMMARY = "${MACHINEBUILD} partitions bootargs files"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit deploy

SRC_URI = "https://source.mynonpublic.com/abcom/${MACHINEBUILD}-bootargs-${SRCDATE}.zip"

S = "${UNPACKDIR}"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0755 ${S}/bootargs.bin ${D}/usr/share/bootargs.bin
}

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINEBUILD}/${MACHINEBUILD}
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINEBUILD}
    install -m 0755 ${S}/${MACHINEBUILD}/bootargs.bin ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINEBUILD}/${MACHINEBUILD}
}

addtask deploy before do_package after do_install

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} = "/usr/share"
