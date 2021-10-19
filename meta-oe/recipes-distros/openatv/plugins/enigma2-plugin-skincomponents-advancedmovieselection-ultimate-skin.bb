SUMMARY = "AdvancedMovieSelection-Ultimate-Skin-by-stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0"
PKGV = "1.0"
VER ="1.0"
PR = "r1"

SRC_URI="git://github.com/stein17/Skins-for-Plugins-by-stein17.git;protocol=https"

S = "${WORKDIR}/git/Ultimate-HD-Skin-for-AMS-by-stein17"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

pkg_postinst_${PN} () {
#!/bin/sh

echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
echo "...Ultimate HD Skin for AdvancedMovieSelection by stein17 successful installed."
echo "                                                                               "
echo "                                                                               "
echo "                                                                               "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh

mv /usr/lib/enigma2/python/Plugins/Extensions/AdvancedMovieSelection/skin/skin.xml_orig /usr/lib/enigma2/python/Plugins/Extensions/AdvancedMovieSelection/skin/skin.xml

echo "syncing disk"
sync
echo ""
echo ""
echo "*********************************************************"
echo "* Ultimate HD Skin for AdvancedMovieSelection by stein17 "
echo "*********************************************************"
echo ""
echo ""
echo ""
echo "Ultimate HD Skin for AdvancedMovieSelection by stein17 successfully removed!"
echo ""
echo ""
echo "GUI restart."
echo ""
echo ""
echo ""
echo "Ultimate HD Skin for AdvancedMovieSelection by stein17 wurde erfolgreich entfernt!"
echo ""
echo ""
echo ""
echo "GUI Neustart."
echo ""
echo ""
echo ""
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh

mv /usr/lib/enigma2/python/Plugins/Extensions/AdvancedMovieSelection/skin/skin.xml /usr/lib/enigma2/python/Plugins/Extensions/AdvancedMovieSelection/skin/skin.xml_orig

echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
echo "  Ultimate HD Skin for AdvancedMovieSelection by stein17 is now being installed... "
echo "                                                                                           "
echo "                                                                                           "
echo "                                                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                                                                 "
echo "Ultimate HD Skin for AdvancedMovieSelection by stein17 is now being removed from your receiver..."
echo "                                                                                                 "
exit 0
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
