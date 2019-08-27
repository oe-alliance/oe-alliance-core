SUMMARY = "Enigma2 Skin Prime-NFR-Style"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r2"

RDEPENDS_${PN} = "enigma2-plugin-skincomponents-weathercomponent, enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI="git://github.com/stein17/Skins-for-openNFR.git"

S = "${WORKDIR}/git/Prime-NFR-Style"

FILES_${PN} = "${libdir} ${datadir}"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}${libdir}/* ${D}${libdir}/
    cp -rp ${S}${datadir}/* ${D}${datadir}/
}

pkg_postrm_${PN} () {
#!/bin/sh
TMP=/tmp/.skins
rm -rf ${datadir}/enigma2/Prime-NFR-Style-by-stein17
rm -rf ${libdir}/enigma2/python/Components/Converter/NextEventsPrime.pyo
rm -rf ${libdir}/enigma2/python/Components/Converter/ReceiverInfoPrime.pyo
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
rm -rf ${datadir}/enigma2/Prime-NFR-Style-by-stein17
rm -rf ${libdir}/enigma2/python/Components/Converter/NextEventsPrime.pyo
rm -rf ${libdir}/enigma2/python/Components/Converter/ReceiverInfoPrime.pyo
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
