SUMMARY = "beyonwizv2 partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20200417"
PR = "${SRCDATE}"

S = "${WORKDIR}/patitions"

SRC_URI = "http://source.mynonpublic.com/beyonwiz/${MACHINE}-partitions-${SRCDATE}.zip"

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
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${S}/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
}

addtask deploy before do_build after do_install

SRC_URI[md5sum] = "763888dcc271d65268c5f6786710bd21"
SRC_URI[sha256sum] = "d2db8736dc62c280f594c9caa6ac5ba45581d179abe9b089d098619360711484"

INSANE_SKIP_${PN} += "already-stripped"

pkg_postinst_${PN}() {
    if [ "x$D" == "x" ]; then
        if [ -f /usr/share/apploader.bin -a -b /dev/block/by-name/loader ] ; then
            dd if=/usr/share/apploader.bin of=/dev/block/by-name/loader
        fi
    fi
    true
}
