SUMMARY = "dBackup Plugin direct backup and Flashing"
MAINTAINER = "gutemine <gutemine@outlook.at>"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "tar pigz xz"

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.8+git${SRCPV}"
PKGV = "2.8+git${GITPKGV}"
VER ="2.8"
PR = "r0"

SRC_URI="git://github.com/openatv/enigma2-plugin-extensions-dbackup.git;branch=python3"

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

do_package_qa[noexec] = "1"
