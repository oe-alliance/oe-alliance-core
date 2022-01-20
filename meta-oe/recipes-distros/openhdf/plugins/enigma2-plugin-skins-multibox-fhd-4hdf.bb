SUMMARY = "Skin Multibox FHD for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent, minilocale"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https;branch=master"

FILES:${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git/Multibox-FHD-Skin-4HDF"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...Multibox Skin Full HD by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Multibox
rm -rf /usr/lib/enigma2/python/Components/Converter/AMB*
rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
}

pkg_preinst:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Multibox
rm -rf /usr/lib/enigma2/python/Components/Converter/AMB
rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB
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

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                           "
echo " Multibox Skin Full HD by stein17 is now being removed...    "
echo "                                                           "
}

do_package_qa[noexec] = "1"

