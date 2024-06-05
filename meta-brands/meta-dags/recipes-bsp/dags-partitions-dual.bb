SUMMARY = "dual partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20230911"
PR = "${SRCDATE}"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

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
    install -m 0755 ${S}/flash-updater ${D}${sysconfdir}/init.d/flash-updater
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0755 ${S}/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINEBUILD}-apploader.bin
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "12ad3f32b350ad014c7c3768bab9eaa1"
SRC_URI[sha256sum] = "ce45ef9e7d6906307111bdea7dacf203e08d2eb7c65de63863ad3631aeef88a8"

INSANE_SKIP:${PN} += "already-stripped"

