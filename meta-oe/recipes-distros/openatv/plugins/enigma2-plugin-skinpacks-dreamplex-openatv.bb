SUMMARY = "Dreamplex BlueMod skins"
MAINTAINER = "stein17"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r2"

PACKAGES = "${EPSM}-bluemod-fhd ${EPSM}-bluemod"

SRC_URI = "git://github.com/oe-alliance/DreamPlexSkins.git;protocol=https;branch=master"

FILES:${EPSM}-bluemod = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/BlueMod"
FILES:${EPSM}-bluemod-fhd = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/BlueMod-FHD"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    cp -rp ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
