SUMMARY = "Full HD Skin for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"

FILES:${PN} = "/"

S = "${WORKDIR}/git/BlueAccents-FHD"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rf ${S}/usr/lib/* ${D}${libdir}
    cp -rf ${S}/usr/share/* ${D}${datadir}
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...BlueAccennts-FHD Skin by stein17 successful installed. "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/BlueAccents-FHD
rm -rf /usr/lib/enigma2/python/Components/Converter/BlueA*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BlueA*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueAccents*
echo "                                                                 "
echo "BlueAccents-FHD skin was successfully removed from your receiver  "
echo "                                                                 "
echo "The GUI of your receiver is now rebooting....                    "
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "Check if a previous version of the BlueAccents-FHD skin is installed"
if [ -f /usr/share/enigma2/BlueAccents-FHD/skin.xml ]; then
    cp -R /usr/share/enigma2/BlueAccents-FHD/ /tmp
    rm -rf /usr/share/enigma2/BlueAccents-FHD
    rm -rf /usr/lib/enigma2/python/Components/Converter/BlueA*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/BlueA*
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueAccents*
    echo "                                                   "
    echo "Previous BlueAccents-FHD skin installation          "
    echo "    was found and removed successfully!            "
    echo "                                                   "
fi
echo "                                                       "
echo "BlueAccents-FHD skin is now being installed...          "
echo "                                                       "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                                           "
echo "BlueAccents-FHD Skin by stein17 is now being removed from your receiver...  "
echo "                                                                           "
exit 0
}
