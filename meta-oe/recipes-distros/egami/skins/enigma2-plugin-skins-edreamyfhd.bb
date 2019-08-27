SUMMARY = "eDreamy FULL HD Skin for EGAMI"
MAINTAINER = "mmark and egami team"
inherit allarch

require conf/license/license-gplv2.inc

RDEPENDS_${EPSM}-edreamyfhd = "enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent enigma2-plugin-extensions-weatherplugin enigma2-plugin-skinscomponents-egskincomponents"

inherit gitpkgv

EPSM = "enigma2-plugin-skins.egami"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r6"

PACKAGES = "${EPSM}-edreamyfhd"
PROVIDES = "${EPSM}-edreamyfhd"

SRC_URI="git://github.com/a4tech/Egami-skins.git;protocol=git"

FILES_${EPSM}-edreamyfhd = "/usr/share/enigma2/eDreamy-FHD /usr/share/enigma2/eDreamy-FHD_ZZ_Mod"

S = "${WORKDIR}/git"

do_compile_append() {
    python -O -m compileall ${S}
}

do_install() {
    cp -rp ${S}/eDreamyFHD/usr ${D}/
}
