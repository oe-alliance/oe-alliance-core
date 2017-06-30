SUMMARY = "direct Backup for Enigma2"
MAINTAINER = "gutemine <gutemine@oozoon.de>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.65+git${SRCPV}"
PKGV = "0.65+git${GITPKGV}"
VER ="0.65"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dbackup.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install_mipsel() {
    cp -rp ${S}/usr ${D}/
    cp -rp ${S}/mipsel/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup
}

do_install_arm() {
    cp -rp ${S}/usr ${D}/
    cp -rp ${S}/arm/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup
}

pkg_postrm_${PN}() {
#!/bin/sh
rm /usr/lib/enigma2/python/Plugins/Extensions/dBackup/*.pyo > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
