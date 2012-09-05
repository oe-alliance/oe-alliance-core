DESCRIPTION = "Skin Red Heat HD. Daconi and mmark"
MAINTAINER = "SIFTeam"

require conf/license/license-gplv2.inc

inherit gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"
SRCREV = ""

SRC_URI = "git://github.com/SIFTeam/skin-adriatic.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2"

RDEPENDS_${PN} = "font-nmsbd2 font-verdanar font-daconi-digital-watch"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/share
	cp -rp ${S}/usr/share/* ${D}/usr/share/
	chmod -R a+rX ${D}/usr/share/enigma2/
}
