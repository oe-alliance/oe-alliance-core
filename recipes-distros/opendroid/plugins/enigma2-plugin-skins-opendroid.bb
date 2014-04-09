SUMMARY = "Enigma2 Skin metrix-HD"
MAINTAINER = "Opendroid"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"


require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r3"

SRC_URI="git://github.com/opendroid-Team/MetrixHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

CONFFILES_${PN} = " \
	/usr/lib/enigma2/python/Plugins/Extensions/iSkin/Weather/Config/Location_id \
	/usr/lib/enigma2/python/Plugins/Extensions/iSkin/Weather/Config/Region_id "

do_install() {
    cp -rp ${S}/usr ${D}/
}