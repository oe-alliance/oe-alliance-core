SUMMARY = "Skyrecorder-FHD-Skin-by-stein17"
MAINTAINER = "stein17"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"
VER ="1.4"
PR = "r1"

SRC_URI="git://github.com/stein17/Skins-for-Plugins-by-stein17.git"

S = "${WORKDIR}/git/Skyrecorder-FHD-Skins-by-stein17"

FILES_${PN} = "/"


do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    chmod -R a+rX ${D}${libdir}/enigma2/
    cp -rp ${S}/tmp ${D}/
}

pkg_postinst_${PN} () {
#!/bin/sh

mv /tmp/stein/SkyAddedEdit.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyAddedEdit.py
mv /tmp/stein/SkyChannelEditor.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelEditor.py
mv /tmp/stein/SkyChannelSelect.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelSelect.py
mv /tmp/stein/SkyGenreSelect.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyGenreSelect.py
mv /tmp/stein/SkyRecorderArchiv.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderArchiv.py
mv /tmp/stein/SkyRecorderMainScreen.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderMainScreen.py
mv /tmp/stein/SkyRecorderSettings.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderSettings.py
mv /tmp/stein/SkySkipWordsSelect.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkySkipWordsSelect.py
mv /tmp/stein/SkyWhitelist.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyWhitelist.py

echo "                                                                           "
echo "  The Skyrecorder-FHD-Skins-by-stein17 is now being installed...           "
echo "                                                                           " 
                                                                     
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh

mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyAddedEdit.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyAddedEdit.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelEditor.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelEditor.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelSelect.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelSelect.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyGenreSelect.py_orig ${libdir}enigma2/python/Plugins/Extensions/skyrecorder/SkyGenreSelect.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderArchiv.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderArchiv.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderMainScreen.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderMainScreen.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderSettings.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderSettings.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkySkipWordsSelect.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkySkipWordsSelect.py
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyWhitelist.py_orig ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyWhitelist.py

echo "syncing disk"
sync
echo ""
echo ""
echo "****************************************"
echo "* FHD Skins for SkyRecorder  by stein17 "
echo "****************************************"
echo ""
echo "FHD Skins for SkyRecorder by stein17 successfully removed!"
echo ""
echo "GUI restart."
echo ""
echo "FHD Skins for SkyRecorder by stein17 wurde erfolgreich entfernt!"
echo ""
echo "GUI Neustart."
echo ""

exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/skins/ax_blue_fhd
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/skins/blue_line_fhd
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/skins/universal_fhd

mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyAddedEdit.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyAddedEdit.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelEditor.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelEditor.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelSelect.py ${libdir}enigma2/python/Plugins/Extensions/skyrecorder/SkyChannelSelect.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyGenreSelect.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyGenreSelect.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderArchiv.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderArchiv.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderMainScreen.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderMainScreen.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderSettings.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyRecorderSettings.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkySkipWordsSelect.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkySkipWordsSelect.py_orig
mv ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyWhitelist.py ${libdir}/enigma2/python/Plugins/Extensions/skyrecorder/SkyWhitelist.py_orig

echo "                                                                           "
echo "  The Skyrecorder-FHD-Skins-by-stein17 is now being installed...           "
echo "                                                                           " 

exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh


echo "                                                                           "
echo "          Skyrecorder-FHD-Skins-by-stein17 is now being removed...         "
echo "                                                                           "

exit 0
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
