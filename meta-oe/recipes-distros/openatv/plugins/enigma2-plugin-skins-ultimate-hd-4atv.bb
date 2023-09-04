SUMMARY = "Ultimate HD Skin for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv 

SRCREV = "${AUTOREV}"
PV = "1.1+git"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"

FILES:${PN} = "/"



S = "${WORKDIR}/git/Ultimate-HD-Skin-4ATV"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
if [ -f /Ultimate/skin.xml ]; then
  rm -rf /usr/share/enigma2/Ultimate/
  mv /tmp/Ultimate /usr/share/enigma2/
fi
echo "                                                          "
echo "   ..Ultimate HD Skin  by stein17 successful installed.   "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Ultimate
rm -rf /usr/lib/enigma2/python/Components/Converter/Ultimate*
rm -rf /usr/lib/enigma2/python/Components/Renderer/Ultimate*
echo "                                                                 "
echo " UltimateHD ATV skin was successfully removed from your receiver "
echo "                                                                 "
echo "      The GUI of your receiver is now rebooting....              "
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo "                                                                            "
echo "                         ***** WARNING ****                                 "
echo "          UltimateHD ATV skin requires a minimal dual core.                 "
echo "    UltimateHD ATV is NOT guaranteed to work on slower or older receivers.  "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "     Check if a previous version of the UltimateHD ATV skin is installed    "
echo "                                                                            "
if [ -f /usr/share/enigma2/Ultimate/skin.xml ]; then
    rm -rf /usr/share/enigma2/Ultimate
    rm -rf /usr/lib/enigma2/python/Components/Converter/Ultimate*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/Ultimate*
    echo "                                                   "
    echo "     Previous UltimateHD-ATV skin installation     "
    echo "        was found and removed successfully!        "
    echo "                                                   "
fi
echo "                                                       "
echo "      UltimateHD ATV skin is now being installed..     "
echo "                                                       "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                                           "
echo "    UltimateHD Skin by stein17 is now being removed from your receiver...  "
echo "                                                                           "
exit 0
}

do_package_qa[noexec] = "1"
