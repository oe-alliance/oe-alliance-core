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
echo "                                                   "
echo "Previous Poster-PLi-HD-FullNight-Mod-stein17 skin installation        "
echo "    was found and removed successfully!            "
echo "                                                   "
echo "                                                       "
echo "Poster-PLi-HD-FullNight-Mod-stein17 is now being installed...        "
echo "                                                       "
exit 0
}