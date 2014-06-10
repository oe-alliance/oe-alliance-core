SUMMARY = "Nou skins by Nashu"
MAINTAINER = "Rossi"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

EPSM = "enigma2-plugin-skins"
SRCREV = "${AUTOREV}"
PV = "6.0+git${SRCPV}"
PKGV = "6.0+git${GITPKGV}"
PR = "r1"

PACKAGES = "${EPSM}-neonovum-hd ${EPSM}-mynovum-hd ${EPSM}-nou-hd ${EPSM}-neonovum-hd-weather ${EPSM}-mynovum-hd-weather ${EPSM}-nou-hd-weather novum-hd-common novum-hd-renderer novum-hd-renderer-src"

RDEPENDS_${EPSM}-neonovum-hd = "novum-hd-renderer novum-hd-common"
RDEPENDS_${EPSM}-neonovum-hd-weather = "novum-hd-renderer novum-hd-common enigma2-plugin-extensions-weatherplugin enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent"
RDEPENDS_${EPSM}-mynovum-hd = "novum-hd-renderer novum-hd-common"
RDEPENDS_${EPSM}-mynovum-hd-weather = "novum-hd-renderer novum-hd-common enigma2-plugin-extensions-weatherplugin enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent"
RDEPENDS_${EPSM}-nou-hd = "novum-hd-renderer novum-hd-common"
RDEPENDS_${EPSM}-nou-hd-weather = "novum-hd-renderer novum-hd-common enigma2-plugin-extensions-weatherplugin enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins.git;protocol=git"

FILES_novum-hd-renderer = "/usr/lib/enigma2/python/Components/Renderer/*.pyo"
FILES_novum-hd-renderer-src = "/usr/lib/enigma2/python/Components/Renderer/*.py"
FILES_novum-hd-common = "/usr/share/enigma2/NOVUM_HD_Common"
FILES_${EPSM}-neonovum-hd = "/usr/share/enigma2/NeoNOVUM_HD"
FILES_${EPSM}-neonovum-hd-weather = "/usr/share/enigma2/NeoNOVUM_HD_Weather"
FILES_${EPSM}-mynovum-hd = "/usr/share/enigma2/myNOVUM_HD"
FILES_${EPSM}-mynovum-hd-weather = "/usr/share/enigma2/myNOVUM_HD_Weather"
FILES_${EPSM}-nou-hd = "/usr/share/enigma2/Nou_HD"
FILES_${EPSM}-nou-hd-weather = "/usr/share/enigma2/Nou_HD_Weather"

S = "${WORKDIR}/git"

do_compile_append() {
   python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Components/Renderer
    mv ${S}/Nou/renderer/* ${D}/usr/lib/enigma2/python/Components/Renderer
    chmod -R a+rX ${D}/usr/lib/enigma2/python/Components/Renderer
    install -d ${D}/usr/share/enigma2
    mv ${S}/Nou/skin/* ${D}/usr/share/enigma2/
    chmod -R a+rX ${D}/usr/share/enigma2/
}
