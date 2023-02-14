SUMMARY = "Full HD Skin for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/${PYTHON_PN}-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "2.2+git${SRCPV}"
PKGV = "2.2+git${GITPKGV}"
VER="2.2"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https;branch=Python3"

FILES:${PN} = "${libdir} ${datadir}"

S = "${WORKDIR}/git/Blue-Line-OCT-4HDF"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/${datadir}/* ${D}${datadir}
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo ""
echo "...Blue-Line-OCT-4HDF by stein17 successful installed."
echo ""
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /enigma2/Blue-Line-OCT-4HDF
rm -rf /usr/lib/enigma2/python/Components/Converter/BL*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL*
echo ""
echo "               ...Skin successful removed."
echo ""
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
rm -rf /enigma2/Blue-Line-OCT-4HDF
rm -rf /usr/lib/enigma2/python/Components/Converter/BL
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL
echo "aktualisiere Updatequellen... "opkg update 
echo ""
echo "Blue-Line-OCT-4HDF by stein17 is now being installed..."
echo ""
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo ""
echo "Blue-Line-OCT-4HDF Skin by stein17 is now being removed..."
echo ""
exit 0
}

do_package_qa[noexec] = "1"
