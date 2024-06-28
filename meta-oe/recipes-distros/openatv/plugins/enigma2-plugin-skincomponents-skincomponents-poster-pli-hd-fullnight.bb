SUMMARY = "Poster-for-PLi-HD-FullNight - Mod by stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "5.2.1"
PKGV = "5.2.1"
VER ="5.2.1"
PR = "r0"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;branch=python3;protocol=https"

S = "${WORKDIR}/git/Skincomponents-Poster-Pli-HD-FullNight-stein17_Mod"

FILES:${PN} = "/usr"

do_install() {
    install -d ${D}/usr
    cp -rp ${S}/usr/* ${D}/usr
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...Poster-PLi-HD-FullNight-Mod-stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/SkinComponents/Poster-PLi-HD-FullNight-Mod-stein17
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModBackdropX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModBackdropXDownloadThread*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModNxtEvntX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModPosterX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModPosterXDownloadThread*
rm -rf /usr/lib/enigma2/python/Components/Renderer/xtrafm*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/xtrafmEvent*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/allScreens/poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/allScreens/skin_Poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/preview/preview_poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/preview/preview_skin_Poster*
echo "                                                                 "
echo "Poster-PLi-HD-FullNight-Mod-stein17 was successfully removed from your receiver"
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
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModBackdropX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModBackdropXDownloadThread*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModNxtEvntX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModPosterX*
rm -rf /usr/lib/enigma2/python/Components/Renderer/PliModPosterXDownloadThread*
rm -rf /usr/lib/enigma2/python/Components/Renderer/xtrafm*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/xtrafmEvent*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/allScreens/poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/allScreens/skin_Poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/preview/preview_poster*
rm -rf /usr/share/enigma2/PLi-HD-FullNight_stein17_Mod/preview/preview_skin_Poster*
echo "                                                   "
echo "Previous Poster-PLi-HD-FullNight-Mod-stein17 skin installation        "
echo "    was found and removed successfully!            "
echo "                                                   "
echo "                                                       "
echo "Poster-PLi-HD-FullNight-Mod-stein17 is now being installed...        "
echo "                                                       "
exit 0
}