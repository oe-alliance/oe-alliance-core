DESCRIPTION = "Skin Red Heat HD. Daconi and mmark"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

inherit allarch
inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"
SRCREV = "${AUTOREV}"

PV_font-nmsbd2 = "20120113"
PR_font-nmsbd2 = "r0"
PKGV_font-nmsbd2 = "${PV_font-nmsbd2}"
DESCRIPTION_font-nmsbd2 = "Nmsbd2 font"

PV_font-verdanar = "20120113"
PR_font-verdanar = "r0"
PKGV_font-verdanar = "${PV_font-verdanar}"
DESCRIPTION_font-verdanar = "Verdana_R font"

PACKAGES = "${PN} font-nmsbd2 font-verdanar"
PROVIDES = "${PN} font-nmsbd2 font-verdanar"

SRC_URI = "git://github.com/SIFTeam/skin-redheathd.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2"
FILES_font-nmsbd2 = "/usr/share/fonts/nmsbd2.ttf"
FILES_font-verdanar = "/usr/share/fonts/Verdana_R.ttf"

RDEPENDS_${PN} = "font-nmsbd2 font-verdanar"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
	chmod 644 ${D}/usr/share/fonts/*.ttf
}
