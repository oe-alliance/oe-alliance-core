SUMMARY = "Skin Multibox FHD for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent, minilocale"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=git"

FILES_${PN} = "${libdir} ${datadir}"

S = "${WORKDIR}/git/Multibox-FHD-Skin-4HDF"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}${libdir}/* ${D}${libdir}/
    cp -rp ${S}${datadir}/* ${D}${datadir}/
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...Multibox Skin Full HD by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf ${datadir}/enigma2/Multibox
rm -rf ${libdir}/enigma2/python/Components/Converter/AMB*
rm -rf ${libdir}/enigma2/python/Components/Renderer/AMB*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
}

pkg_preinst_${PN} () {
#!/bin/sh
rm -rf ${datadir}/enigma2/Multibox
rm -rf ${libdir}/enigma2/python/Components/Converter/AMB
rm -rf ${libdir}/enigma2/python/Components/Renderer/AMB
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "  Multibox Skin Full HD by stein17 is now being installed...               "
echo "                                                                           " 
echo "                                                                           "
echo "                                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo " Multibox Skin Full HD by stein17 is now being removed...    "
echo "                                                           "
}

do_package_qa[noexec] = "1"

