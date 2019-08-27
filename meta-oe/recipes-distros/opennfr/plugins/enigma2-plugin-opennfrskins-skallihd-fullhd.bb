SUMMARY = "Enigma2 Skin OpenNFR-SkalliHD-FullHD"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r1"

SRC_URI="git://github.com/carlo0815/SkalliHD-FullHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir} ${datadir}"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}${libdir}/* ${D}${libdir}/
    cp -rp ${S}${datadir}/* ${D}${datadir}/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
