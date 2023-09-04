SUMMARY = "dBackup Plugin direct backup and Flashing"
MAINTAINER = "gutemine <gutemine@oozoon.de>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "tar pigz xz"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.8+git"
PKGV = "2.8+git${GITPKGV}"
VER ="2.8"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dbackup.git;branch=python3;protocol=https"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir}"

do_install:mipsel() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/mipsel/* ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup
}

do_install:arm() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr ${D}/
    cp -rp ${S}/arm/* ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup/
    chmod -R 777 ${D}${libdir}/enigma2/python/Plugins/Extensions/dBackup
}

require conf/python/python3-compileall.inc

do_package_qa[noexec] = "1"
