SUMMARY = "Dreamplex YouViX skins by rossi2000"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r4"

PACKAGES = "${EPSM}-youvix-red ${EPSM}-youvix-green ${EPSM}-youvix-darkblue"

SRC_URI = "git://github.com/rossi2000/DreamPlexSkins.git;protocol=git"

FILES_${EPSM}-youvix-red = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Red"
FILES_${EPSM}-youvix-green = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Green"
FILES_${EPSM}-youvix-darkblue = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-DarkBlue"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    mv ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}
