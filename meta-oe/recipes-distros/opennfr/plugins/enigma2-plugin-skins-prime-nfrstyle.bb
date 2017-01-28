SUMMARY = "Enigma2 Skin Prime-NFR-Style"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "${GITPKGVTAG}"
VER ="2.1"
PR = "r1"

RDEPENDS_${PN} = "enigma2-plugin-skincomponents-weathercomponent, enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI="git://github.com/stein17/Prime-NFR-Style.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_postrm_${PN} () {
#!/bin/sh
TMP=/tmp/.skins
rm -rf /usr/share/enigma2/Prime-NFR-Style-by-stein17
rm -rf /usr/lib/enigma2/python/Components/Converter/NextEventsPrime.pyo
rm -rf /usr/lib/enigma2/python/Components/Converter/ReceiverInfoPrime.pyo
echo "syncing disk"
sync
echo ""
echo ""
echo "****************************************"
echo "*            Prime-NFR-Style            "
echo "*        openNFR Skin by stein17        "
echo "*    powered by Nachtfalke reloaded     "
echo "****************************************"
echo ""
echo ""
echo ""
echo "Prime-NFR-Style-by-stein17 successfully removed!"
echo ""
echo ""
echo "GUI restart."
echo ""
echo ""
echo ""
echo "Prime-NFR-Style-by-stein17 wurde erfolgreich entfernt!"
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
rm -rf /usr/share/enigma2/Prime-NFR-Style-by-stein17
rm -rf /usr/lib/enigma2/python/Components/Converter/NextEventsPrime.pyo
rm -rf /usr/lib/enigma2/python/Components/Converter/ReceiverInfoPrime.pyo
echo "aktualisiere Updatequellen... "opkg update 
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "########  ########  #### ##     ## ########    ##    ## ######## ########  " 
echo "##     ## ##     ##  ##  ###   ### ##          ###   ## ##       ##     ## "
echo "##     ## ##     ##  ##  #### #### ##          ####  ## ##       ##     ## "
echo "########  ########   ##  ## ### ## ######      ## ## ## ######   ########  "
echo "##        ##   ##    ##  ##     ## ##          ##  #### ##       ##   ##   "
echo "##        ##    ##   ##  ##     ## ##          ##   ### ##       ##    ##  "
echo "##        ##     ## #### ##     ## ########    ##    ## ##       ##     ## "
echo "                  ######  ######## ##    ## ##       ########              "
echo "                ##    ##    ##     ##  ##  ##       ##                     "
echo "                ##          ##      ####   ##       ##                     "
echo "                 ######     ##       ##    ##       ######                 "
echo "                      ##    ##       ##    ##       ##                     "
echo "                ##    ##    ##       ##    ##       ##                     "
echo "                 ######     ##       ##    ######## ########               "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "            The Skin Prime NFR Style is now being installed...             "
echo "                                                                           " 
echo "                                                                           "
echo "                                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "########  ########  #### ##     ## ########    ##    ## ######## ########  " 
echo "##     ## ##     ##  ##  ###   ### ##          ###   ## ##       ##     ## "
echo "##     ## ##     ##  ##  #### #### ##          ####  ## ##       ##     ## "
echo "########  ########   ##  ## ### ## ######      ## ## ## ######   ########  "
echo "##        ##   ##    ##  ##     ## ##          ##  #### ##       ##   ##   "
echo "##        ##    ##   ##  ##     ## ##          ##   ### ##       ##    ##  "
echo "##        ##     ## #### ##     ## ########    ##    ## ##       ##     ## "
echo "                  ######  ######## ##    ## ##       ########              "
echo "                ##    ##    ##     ##  ##  ##       ##                     "
echo "                ##          ##      ####   ##       ##                     "
echo "                 ######     ##       ##    ##       ######                 "
echo "                      ##    ##       ##    ##       ##                     "
echo "                ##    ##    ##       ##    ##       ##                     "
echo "                 ######     ##       ##    ######## ########               "
echo "                                                                           "
echo "                                                                           "
echo "             The Skin Prime NFR Style is now being removed...              "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
