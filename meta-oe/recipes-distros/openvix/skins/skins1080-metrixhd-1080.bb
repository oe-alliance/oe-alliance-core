SUMMARY = "Metrix FULL HD skin by scwheeler"
MAINTAINER = "scwheeler"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins1080"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

PACKAGES = "metrixhd-1080-renderer metrixhd-1080-renderer-src metrixhd-1080-converter metrixhd-1080-converter-src ${EPSM}-metrixhd-1080"
PROVIDES = "metrixhd-1080-renderer metrixhd-1080-renderer-src metrixhd-1080-converter metrixhd-1080-converter-src ${EPSM}-metrixhd-1080"

SRC_URI = "git://github.com/OpenViX/vixhd-skins.git;protocol=git"

FILES_metrixhd-1080-renderer = "/usr/lib/enigma2/python/Components/Renderer/*.pyo"
FILES_metrixhd-1080-renderer-src = "/usr/lib/enigma2/python/Components/Renderer/*.py"
FILES_metrixhd-1080-converter = "/usr/lib/enigma2/python/Components/Converter/*.pyo"
FILES_metrixhd-1080-converter-src = "/usr/lib/enigma2/python/Components/Converter/*.py"
FILES_${EPSM}-metrixhd-1080 = "/usr/share/enigma2/MetrixHD_1080"

RDEPENDS_${EPSM}-metrixhd-1080 = "metrixhd-1080-renderer metrixhd-1080-converter "

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    mv ${S}/MetrixHD_1080/renderer/* ${D}/usr/lib/enigma2/python/Components/Renderer
	install -d ${D}/usr/lib/enigma2/python/Components/Converter
    mv ${S}/MetrixHD_1080/converter/* ${D}/usr/lib/enigma2/python/Components/Converter
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Renderer
	chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Converter
    install -d ${D}/usr/share/enigma2
    mv ${S}/MetrixHD_1080/skin/* ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
