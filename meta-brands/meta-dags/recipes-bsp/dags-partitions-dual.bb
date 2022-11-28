SUMMARY = "dual partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20220915"
PR = "${SRCDATE}"

S = "${WORKDIR}"

SRC_URI = "http://en3homeftp.net/down/dags-partitions-${MACHINEBUILD}_${SRCDATE}.tar.gz \
 file://flash-updater \
"

inherit update-rc.d

INITSCRIPT_NAME = "flash-updater"
INITSCRIPT_PARAMS = "start 90 S ."

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${S}/apploader.bin ${D}/usr/share/apploader.bin
    install -m 0644 ${S}/fastboot.bin ${D}/usr/share/fastboot.bin
    install -m 0644 ${S}/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/flash-updater ${D}${sysconfdir}/init.d/flash-updater
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-apploader.bin
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "076cda40a60b602c28daaa08dbbbcfcf"
SRC_URI[sha256sum] = "71cd0306028a64ab37ab80737355a2d88d1cf13b87e02e8184fa2eb3e49e41b5"

INSANE_SKIP:${PN} += "already-stripped"

