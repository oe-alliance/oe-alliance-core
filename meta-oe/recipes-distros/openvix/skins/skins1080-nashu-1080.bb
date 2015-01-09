SUMMARY = "FULL HD skins by nashu"
MAINTAINER = "nashu"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins1080"
SRCREV = "${AUTOREV}"
PV = "0.4+git${SRCPV}"
PKGV = "0.4+git${GITPKGV}"
PR = "r1"

PACKAGES = "nashu-1080-renderer nashu-1080-renderer-src ${EPSM}-mynovum_fhd2_black-1080 ${EPSM}-novum_fhd_slim-1080"

SRC_URI = "git://github.com/OpenViX/vixhd-skins.git;protocol=git"

FILES_nashu-1080-renderer = "/usr/lib/enigma2/python/Components/Renderer/*.pyo"
FILES_nashu-1080-renderer-src = "/usr/lib/enigma2/python/Components/Renderer/*.py"
FILES_${EPSM}-mynovum_fhd2_black-1080 = "/usr/share/enigma2/myNOVUM_FHD2_Black"
FILES_${EPSM}-novum_fhd_slim-1080 = "/usr/share/enigma2/NOVUM_FHD_Slim"

RDEPENDS_${EPSM}-mynovum_fhd2_black-1080 = "nashu-1080-renderer"
RDEPENDS_${EPSM}-novum_fhd_slim-1080 = "nashu-1080-renderer"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    mv ${S}/Nashu_1080/renderer/* ${D}/usr/lib/enigma2/python/Components/Renderer
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Renderer
    install -d ${D}/usr/share/enigma2
    mv ${S}/Nashu_1080/skin/* ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
