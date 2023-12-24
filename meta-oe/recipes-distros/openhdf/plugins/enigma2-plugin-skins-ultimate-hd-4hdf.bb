SUMMARY = "Full HD Skin for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/${PYTHON_PN}-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.2+git"
PKGV = "1.2+git${GITPKGV}"
VER="1.2"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https;branch=Python3"

FILES:${PN} = "${libdir} ${datadir}"

S = "${WORKDIR}/git/Ultimate-HD-Skin-4HDF"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/${datadir}/* ${D}${datadir}
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
if [ -f /Ultimate/skin.xml ]; then
  rm -rf /enigma2/Ultimate/
  mv /tmp/Ultimate /enigma2/
fi
echo ""
echo "...Ultimate HD Skin  by stein17 successful installed."
echo ""
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /enigma2/Ultimate
rm -rf /usr/lib/enigma2/python/Components/Converter/Ultimate*
rm -rf /usr/lib/enigma2/python/Components/Renderer/Ultimate*
echo ""
echo " UltimateHD HDF skin was successfully removed from your receiver"
echo ""
echo "      The GUI of your receiver is now rebooting...."
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo ""
echo "                         ***** WARNING ****"
echo "          UltimateHD HDF skin requires a minimal dual core."
echo "    UltimateHD HDF is NOT guaranteed to work on slower or older receivers."
echo ""
echo ""
echo ""
echo "     Check if a previous version of the UltimateHD HDF skin is installed"
echo ""
if [ -f /enigma2/Ultimate/skin.xml ]; then
    rm -rf /enigma2/Ultimate
    rm -rf /usr/lib/enigma2/python/Components/Converter/Ultimate*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/Ultimate*
    echo ""
    echo "     Previous UltimateHD-HDF skin installation"
    echo "        was found and removed successfully!"
    echo ""
fi
echo ""
echo "UltimateHD HDF skin is now being installed.."
echo ""
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo ""
echo "UltimateHD Skin by stein17 is now being removed..."
echo ""
exit 0
}

do_package_qa[noexec] = "1"
