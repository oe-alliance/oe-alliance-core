SUMMARY = "DreamPlex YouViX-Blue skin by rossi2000"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/rossi2000/DreamPlexSkins.git;protocol=git"

FILES_${PN} = "/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/YouViX-Blue"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    mv ${S}/YouViX-Blue ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Plugins/Extensions/DreamPlex/skins/
}
