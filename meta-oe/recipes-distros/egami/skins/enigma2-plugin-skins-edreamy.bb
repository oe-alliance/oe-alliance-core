SUMMARY = "eDreamy Skin for EGAMI"
MAINTAINER = "mmark and egami team"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

RDEPENDS_${EPSM}-edreamy = "enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent enigma2-plugin-extensions-weatherplugin enigma2-plugin-skinscomponents-egskincomponents"

inherit gitpkgv

EPSM = "enigma2-plugin-skins.egami"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r11"

PACKAGES = "${EPSM}-edreamy"
PROVIDES = "${EPSM}-edreamy"

SRC_URI="git://github.com/a4tech/Egami-skins.git;protocol=git"

FILES_${EPSM}-edreamy = "/usr/share/enigma2/eDreamy /usr/share/enigma2/eDreamyZZ"

S = "${WORKDIR}/git"

do_compile_append() {
    python -O -m compileall ${S}
}

do_install() {
    cp -rp ${S}/eDreamy/usr ${D}/
}
