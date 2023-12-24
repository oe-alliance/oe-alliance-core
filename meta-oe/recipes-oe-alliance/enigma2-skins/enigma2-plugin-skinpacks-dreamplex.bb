SUMMARY = "Dreamplex skinpacks"
MAINTAINER = "rossi2000 and stein17"
inherit allarch

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r1"

PACKAGES = "\
    ${EPSM}-youplex-blue \
    ${EPSM}-youplex-red \
    ${EPSM}-youplex-green \
    ${EPSM}-youplex-purple \
    ${EPSM}-plex-experience \
    "

SRC_URI = "git://github.com/oe-alliance/DreamPlexSkins.git;protocol=https;branch=master"

FILES:${EPSM}-youplex-blue = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Blue"
FILES:${EPSM}-youplex-red = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Red"
FILES:${EPSM}-youplex-green = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Green"
FILES:${EPSM}-youplex-purple = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Purple"
FILES:${EPSM}-plex-experience = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/Plex_Experience"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    cp -rp ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
