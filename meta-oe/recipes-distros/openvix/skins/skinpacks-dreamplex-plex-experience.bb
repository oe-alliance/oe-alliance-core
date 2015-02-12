SUMMARY = "Plex Experience by markus625"
MAINTAINER = "markus625"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r0"

PACKAGES = "${EPSM}-plex-experience"

SRC_URI = "git://github.com/rossi2000/DreamPlexSkins.git;protocol=git"

FILES_${EPSM}-plex-experience = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/Plex_Experience"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    mv ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}
