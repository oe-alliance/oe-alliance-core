SUMMARY = "HD Skin for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https;branch=Python3"

FILES:${PN} = "${libdir} ${datadir}"

S = "${WORKDIR}/git/BlueAccents-HD-4HDF"

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
echo "...BlueAccennts-HD Skin by stein17 successful installed."
echo ""
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /enigma2/BlueAccents-HD
rm -rf /usr/lib/enigma2/python/Components/Converter/BlueA*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BlueA*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueAccents*
echo ""
echo "BlueAccents-HD skin was successfully removed from your receiver"
echo ""
echo "The GUI of your receiver is now rebooting...."
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo "Check if a previous version of the BlueAccents-HD skin is installed"
if [ -f /enigma2/BlueAccents-HD/skin.xml ]; then
    cp -R /enigma2/BlueAccents-HD/ /tmp
    rm -rf /enigma2/BlueAccents-HD
    rm -rf /usr/lib/enigma2/python/Components/Converter/BlueA*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/BlueA*
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueAccents*
    echo ""
    echo "Previous BlueAccents-HD skin installation"
    echo "    was found and removed successfully!"
    echo ""
fi
echo ""
echo "BlueAccents-HD skin is now being installed..."
echo ""
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo ""
echo "BlueAccents-HD skin is now being removed..."
echo ""
exit 0
}

do_package_qa[noexec] = "1"
