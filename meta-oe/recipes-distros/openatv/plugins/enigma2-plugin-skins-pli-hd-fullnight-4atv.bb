SUMMARY = "Enigma2 Skin PLi-HD-FullNight stein17 Mod"
MAINTAINER = "stein17"
require conf/license/license-gplv2.inc

require conf/python/python3-compileall.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"

FILES:${PN} = "/"

S = "${WORKDIR}/git/PLi-HD-FullNight_stein17_Mod"

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
echo " ...PLi-HD-FullNight_stein17_Mod successful installed. "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/PLiHDFullNightMod*
echo "                                                                 "
echo "PLi-HD-FullNight_stein17_Mod skin was successfully removed from your receiver  "
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
echo "Check if a previous version of the PLi-HD-FullNight_stein17_Mod is installed"
if [ -f /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/skin.xml ]; then
    cp -R /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/ /tmp
    rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/PLiHDFullNightMod*
    echo "                                                   "
    echo "Previous PLi-HD-FullNight_stein17_Mod skin installation          "
    echo "    was found and removed successfully!            "
    echo "                                                   "
fi
echo "                                                       "
echo "PLi-HD-FullNight_stein17_Mod skin is now being installed...          "
echo "                                                       "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                                           "
echo "PLi-HD-FullNight_stein17_Mod is now being removed from your receiver...  "
echo "                                                                           "
exit 0
}

INSANE_SKIP:${PN} += "build-deps"
