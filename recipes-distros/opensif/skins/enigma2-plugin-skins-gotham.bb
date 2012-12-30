DESCRIPTION = "Skin Gotham by MMark"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

inherit allarch
inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/SIFTeam/skin-gotham.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2"

RDEPENDS_${PN} = "font-nmsbd2 font-verdanar"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
