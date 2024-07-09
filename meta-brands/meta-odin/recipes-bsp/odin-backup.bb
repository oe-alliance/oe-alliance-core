SUMMARY = "odin backup"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL-2.0-only"

require conf/license/license-gplv2.inc

COMPATIBLE_MACHINE = "^(odinm7$|odinm9)$"

PV = "1.0"
PR = "r5"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://odin-backup.sh"

do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/rc3.d
    install -m 0755 ${UNPACKDIR}/odin-backup.sh ${D}${sysconfdir}/init.d
    ln -sf    ../init.d/odin-backup.sh ${D}${sysconfdir}/rc3.d/S30odin-backup
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
