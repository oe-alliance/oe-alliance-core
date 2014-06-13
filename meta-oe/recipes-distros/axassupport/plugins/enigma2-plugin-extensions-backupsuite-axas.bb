SUMMARY = "enigma2-plugin-extensions-backupsuite-axas"
MAINTAINER = "axas"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.3+git${SRCPV}"
PKGV = "1.3+git${GITPKGV}"
VER ="1.3"
PR = "r0"

SRC_URI="git://github.com/AXAS/enigma2-plugin-extensions-backupsuite-axas.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
    chmod 777 ${D}/usr/bin/backupsuite.sh
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/BackupSuite-HDD
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/BackupSuite-USB
}