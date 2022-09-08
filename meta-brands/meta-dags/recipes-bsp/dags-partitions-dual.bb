SUMMARY = "dual partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20220907"
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
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-apploader.bin
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "0575b81b5222340fc75f91c4796324fb"
SRC_URI[sha256sum] = "0cdbab9db59263daa3a3adedf493e9b5fb444d23fd63bc9012f62ae15239ac0a"

INSANE_SKIP:${PN} += "already-stripped"

