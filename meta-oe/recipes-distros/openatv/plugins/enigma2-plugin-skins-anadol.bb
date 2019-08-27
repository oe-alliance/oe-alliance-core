SUMMARY = "Full HD Skin for Anadol Boxes"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

RDEPENDS_${PN} = "enigma2-plugin-skincomponents-weathercomponent, enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=git"

FILES_${PN} = "/"



S = "${WORKDIR}/git/Anadol"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "                                                          "
echo "    Anadol Skin Full HD by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Anadol
rm -rf ${libdir}/enigma2/python/Components/Converter/AND*
rm -rf ${libdir}/enigma2/python/Components/Renderer/AND*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh                                                       
rm -rf /usr/share/enigma2/Anadol
rm -rf ${libdir}/enigma2/python/Components/Converter/AND*
rm -rf ${libdir}/enigma2/python/Components/Renderer/AND*
echo "                                                                           "
echo "  Anadol Skin Full HD by stein17 is now being installed...                 "
echo "                                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo " Anadol Skin Full HD by stein17 is now being removed...    "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"

