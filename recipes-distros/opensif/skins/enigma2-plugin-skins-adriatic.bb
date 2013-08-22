DESCRIPTION = "Skin Adriatic HD. Daconi"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

inherit allarch
inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r3"
SRCREV = "${AUTOREV}"

PV_font-daconi-digital-bold = "20120921"
PR_font-daconi-digital-bold = "r0"
PKGV_font-daconi-digital-bold = "${PV_font-daconi-digital-bold}"
DESCRIPTION_font-daconi-digital-bold = "Daconi digital Bold font"

PACKAGES = "${PN} font-daconi-digital-bold"
PROVIDES = "${PN} font-daconi-digital-bold"

SRC_URI = "git://github.com/SIFTeam/skin-adriatic.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2"
FILES_font-daconi-digital-bold = "/usr/share/fonts/Daconi-digital-Bold.ttf"

RDEPENDS_${PN} = "font-nmsbd2 font-verdanar font-daconi-digital-watch font-daconi-digital-bold"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
	chmod 644 ${D}/usr/share/fonts/*.ttf
}
