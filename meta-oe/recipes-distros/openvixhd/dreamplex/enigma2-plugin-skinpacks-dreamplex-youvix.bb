SUMMARY = "Dreamplex YouViX skins by rossi2000"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skinpacks-dreamplex"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r2"

PACKAGES = "${EPSM}-youvix-blue ${EPSM}-youvix-red ${EPSM}-youvix-common"

RDEPENDS_${EPSM}-youvix-blue = "${EPSM}-youvix-common"
RDEPENDS_${EPSM}-youvix-red = "${EPSM}-youvix-common"

SRC_URI = "git://github.com/rossi2000/DreamPlexSkins.git;protocol=git"

FILES_${EPSM}-youvix-common = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Common"
FILES_${EPSM}-youvix-blue = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Blue"
FILES_${EPSM}-youvix-red = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Red"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    mv ${S}/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}
