SUMMARY = "Dreamplex FHD skins"
MAINTAINER = "rossi2000"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"
PR = "r1"

PACKAGES = "${EPSM}-youplex-blue ${EPSM}-youplex-red ${EPSM}-youplex-green ${EPSM}-youplex-purple ${EPSM}-plex-experience"

SRC_URI = "git://github.com/OpenViX/DreamPlexSkins.git;protocol=https"

FILES_${EPSM}-youplex-blue = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Blue"
FILES_${EPSM}-youplex-red = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Red"
FILES_${EPSM}-youplex-green = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Green"
FILES_${EPSM}-youplex-purple = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouPlex-Purple"
FILES_${EPSM}-plex-experience = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/Plex_Experience"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    cp -rp ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
