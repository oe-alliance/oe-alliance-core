DESCRIPTION = "Skin Jules Black HD. Daconi"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

inherit allarch
inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"
SRCREV = "${AUTOREV}"

PV_font-daconi-digital-watch = "${PV}"
PR_font-daconi-digital-watch = "r0"
PKGV_font-daconi-digital-watch = "${PKGV}"
DESCRIPTION_font-daconi-digital-watch = "Daconi digital watch font"

PV_enigma2-plugin-skins-jules-black-hd-supremacy = "${PV}"
PR_enigma2-plugin-skins-jules-black-hd-supremacy = "r0"
PKGV_enigma2-plugin-skins-jules-black-hd-supremacy = "${PKGV}"
DESCRIPTION_enigma2-plugin-skins-jules-black-hd-supremacy = "Skin Jules Black HD Supremacy. Daconi"

PACKAGES = "${PN} font-daconi-digital-watch enigma2-plugin-skins-jules-black-hd-supremacy"
PROVIDES = "${PN} font-daconi-digital-watch enigma2-plugin-skins-jules-black-hd-supremacy"

SRC_URI = "git://github.com/SIFTeam/skin-julesblackhd.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2/jules_black_hd"
FILES_enigma2-plugin-skins-jules-black-hd-supremacy = "/usr/share/enigma2/jules_black_hd_supremacy"
FILES_font-daconi-digital-watch = "/usr/share/fonts/Daconi-digital-watch.ttf"

RDEPENDS_${PN} = "font-nmsbd2 font-verdanar font-daconi-digital-watch"
RDEPENDS_enigma2-plugin-skins-jules-black-hd-supremacy = "${PN}"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
	chmod 644 ${D}/usr/share/fonts/*.ttf
}
