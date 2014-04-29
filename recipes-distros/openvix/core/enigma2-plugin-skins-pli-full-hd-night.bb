SUMMARY = "PLi HD skin"
MAINTAINER = "delagroov"
LICENSE = "Proprietary"
require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0"
PACKAGE_ARCH = "all"

SRC_URI = "git://github.com/OpenViX/skin-PLi-Full-HD-Night.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2/"

RDEPENDS_${PN} = "font-valis-hd"

S = "${WORKDIR}/git"

do_compile() {
}

do_install() {
    install -d ${D}/usr/share
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
