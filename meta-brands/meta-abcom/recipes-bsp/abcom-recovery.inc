SUMMARY = "${MACHINEBUILD} parameters files"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINEBUILD}"

inherit deploy

SRC_URI = "https://source.mynonpublic.com/abcom/${MACHINEBUILD}-recovery-${SRCDATE}.zip"

S = "${UNPACKDIR}"

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINEBUILD}/${MACHINEBUILD}
    install -m 0755 ${S}/recovery.bin ${DEPLOY_DIR_IMAGE}/abcom-partitions-${MACHINEBUILD}/${MACHINEBUILD}/recovery.bin
}

addtask deploy before do_package after do_install

INSANE_SKIP:${PN} += "already-stripped"
