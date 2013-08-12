DESCRIPTION = "Modification is based on open-source skin from Army. It is adapted to work on OpenPLi, OpenViX, OpenATV images."
MAINTAINER = "Taykun345/OldSkulRide from OpenPLi/ViX forums"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

PACKAGES += "armymoodbluehd-components"
PROVIDES += "armymoodbluehd-components"

SRC_URI="git://github.com/Taykun345/skin-PLi-Army-MoodBlueHD-mod.git;protocol=git"

FILES_armymoodbluehd-components = "/usr/lib/enigma2/python/Components/Converter"
FILES_${PN} = "/usr/share/enigma2/Army_MoodBlue_mod"

RDEPENDS_${PN} = "armymoodbluehd-components"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/usr/
	cp -rp ${S}/usr/* ${D}/usr/
	install -d ${D}/tmp
}
