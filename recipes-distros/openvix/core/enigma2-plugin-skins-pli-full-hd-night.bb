SUMMARY = "PLi HD skin"
MAINTAINER = "delagroov"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/share/enigma2/PLi-Full-HD-Night/skin.xml;beginline=3;endline=8;md5=1d560d35b9194281a488eb3a32d9c8bf"

inherit gitpkgv

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
