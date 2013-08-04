DESCRIPTION = "PLi HD skin"
MAINTAINER = "littlesat"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/share/enigma2/PLi-HD/skin.xml;beginline=3;endline=8;md5=1d560d35b9194281a488eb3a32d9c8bf"

inherit gitpkgv

PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0"
PACKAGE_ARCH = "all"

PV_font-valis-hd = "2010.05.14"
PR_font-valis-hd = "r1"
PKGV_font-valis-hd = "${PV_font-valis-hd}"
DESCRIPTION_font-valis-hd = "Valis hd fonts"
FILES_font-valis-hd = "/usr/share/fonts/hd.ttf /usr/share/fonts/hdi.ttf"

PACKAGES = "${PN} font-valis-hd"
PROVIDES = "${PN} font-valis-hd"

SRC_URI = "git://github.com/littlesat/skin-PLiHD.git;protocol=git"

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
