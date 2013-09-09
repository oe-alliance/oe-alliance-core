DESCRIPTION = "oe-alliance skins for Enigma2"
MAINTAINER = "oe-alliance"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r5"

PACKAGES = "${EPSM}-neonovum-hd ${EPSM}-mynovum-hd novum-hd-renderer ${EPSM}-novum-hd-renderer-src"
PROVIDES = "${PN} ${EPSM}-neonovum-hd ${EPSM}-mynovum-hd novum-hd-renderer ${EPSM}-novum-hd-renderer-src"
RDEPENDS_${EPSM}-neonovum-hd = "novum-hd-renderer"
RDEPENDS_${EPSM}-mynovum-hd = "novum-hd-renderer"

SRC_URI="git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_novum-hd-renderer = "/usr/lib/enigma2/python/Components/Renderer/*.pyo"
FILES_${EPSM}-novum-hd-renderer-src = "/usr/lib/enigma2/python/Components/Renderer/*.py"
FILES_${EPSM}-neonovum-hd = "/usr/share/enigma2/NeoNOVUM_HD"
FILES_${EPSM}-mynovum-hd = "/usr/share/enigma2/myNOVUM_HD"

S = "${WORKDIR}/git"

do_compile_append() {
	python -O -m compileall ${S}
}

do_install() {
	install -d ${D}/usr/lib/enigma2/python/Components/Renderer
	mv ${S}/NOVUM_HD_Renderer/* ${D}/usr/lib/enigma2/python/Components/Renderer
	chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Renderer

	install -d ${D}/usr/share/enigma2
	mv ${S}/myNOVUM_HD ${D}/usr/share/enigma2/
	mv ${S}/NeoNOVUM_HD ${D}/usr/share/enigma2/
	chmod -R a+rX ${D}/usr/share/enigma2/

}
