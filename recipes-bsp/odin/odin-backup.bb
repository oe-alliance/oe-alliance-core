SUMMARY = "odin backup"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPLv2"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"

SRC_URI = "file://odin-backup.sh \
           "

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${WORKDIR}/odin-backup.sh ${D}${sysconfdir}/init.d
    ln -sf    ../init.d/odin-backup.sh ${D}${sysconfdir}/rc3.d/S30odin-backup
}

PACKAGE_ARCH := "${MACHINE_ARCH}"