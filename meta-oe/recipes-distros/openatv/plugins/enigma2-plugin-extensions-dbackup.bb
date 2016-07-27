SUMMARY = "direct Backup for Enigma2"
MAINTAINER = "gutemine <gutemine@oozoon.de>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "0.48+git${SRCPV}"
PKGV = "0.31+git${GITPKGV}"
VER ="0.48"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dbackup.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
    chmod -R 777 ${D}/usr/lib/enigma2/python/Plugins/Extensions/dBackup
}

pkg_postrm_${PN}() {
#!/bin/sh
rm /usr/lib/enigma2/python/Plugins/Extensions/dBackup/*.pyo > /dev/null 2>&1
exit 0
}

do_package_qa[noexec] = "1"
