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

FILES_${PN} = "${libdir}"

do_install_mipsel() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/mipsel/* ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup
}

do_install_arm() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr ${D}/
    cp -rp ${S}/arm/* ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup
}

pkg_postrm_${PN}() {
#!/bin/sh
rm ${libdir}/enigma2/python/Plugins/Extensions/dBackup/*.pyo > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
