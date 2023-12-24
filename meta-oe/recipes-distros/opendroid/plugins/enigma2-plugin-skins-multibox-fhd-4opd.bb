SUMMARY = "Skin Multibox FHD for OPD Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1+git"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openOPD.git;protocol=https;branch=python3"

FILES:${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git/Multibox-FHD-Skin-4OPD"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
if [ -f /Multibox/skin.xml ]; then
  rm -rf /usr/share/enigma2/Multibox/
  mv /tmp/Multibox /usr/share/enigma2/
fi
echo "                                                          "
echo " ..Multibox Skin Full HD by stein17 successful installed. "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Multibox
rm -rf /usr/lib/enigma2/python/Components/Converter/AMB*
rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB*
echo "                                                                 "
echo "Multibox FHD OPD skin was successfully removed from your receiver"
echo "                                                                 "
echo "The GUI of your receiver is now rebooting....                    "
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo "                                                                            "
echo "                         ***** WARNING ****                                 "
echo "Multibox FHD OPD skin requires a minimal dual core.                         "
echo "Multibox FHD OPD is NOT guaranteed to work on slower or older receivers.    "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "Check if a previous version of the Multibox FHD OPD skin is installed"
if [ -f /usr/share/enigma2/Multibox/skin.xml ]; then
    cp -R /usr/share/enigma2/Multibox/ /tmp
    rm -rf /usr/share/enigma2/Multibox
    rm -rf /usr/lib/enigma2/python/Components/Converter/AMB*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB*
    echo "                                                   "
    echo "Previous Multibox FHD-OPD skin installation        "
    echo "    was found and removed successfully!            "
    echo "                                                   "
fi
echo "                                                       "
echo "Multibox FHD OPD skin is now being installed...        "
echo "                                                       "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                                           "
echo "Multibox Skin Full HD by stein17 is now being removed from your receiver..."
echo "                                                                           "
exit 0
}

do_package_qa[noexec] = "1"
