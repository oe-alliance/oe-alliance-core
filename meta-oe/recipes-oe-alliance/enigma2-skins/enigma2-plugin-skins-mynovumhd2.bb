SUMMARY = "myNOVUM_HD2 by Nashu"
MAINTAINER = "rossi2000"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "16.4+git${SRCPV}"
PKGV = "16.4+git${GITPKGV}"
PR = "r2"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_${PN} = "/usr/share/enigma2/myNOVUM_HD2"
FILES_${PN} += "/usr/lib/enigma2/python/Components/Renderer"
FILES_${PN} += "/usr/lib/enigma2/python/Components/Converter"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
   install -d ${D}/usr/lib/enigma2/python/Components/Renderer
   mv ${S}/Nashu/renderer/* ${D}/usr/lib/enigma2/python/Components/Renderer
   chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Renderer
   install -d ${D}/usr/lib/enigma2/python/Components/Converter
   mv ${S}/Nashu/converter/* ${D}/usr/lib/enigma2/python/Components/Converter
   chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Converter
   install -d ${D}/usr/share/enigma2
   mv ${S}/Nashu/myNOVUM_HD2 ${D}/usr/share/enigma2/
   chmod -R a+rX ${D}/usr/share/enigma2/
}
