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

RDEPENDS:${PN} = "enigma2-plugin-skincomponents-weathercomponent, enigma2-plugin-systemplugins-weathercomponenthandler"

SRC_URI="git://github.com/stein17/Skins-for-openNFR.git;protocol=https;branch=6.5"

S = "${WORKDIR}/git/Prime-NFR-Style"

FILES:${PN} = "${libdir} /usr/share"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
}

pkg_postrm:${PN} () {
#!/bin/sh
TMP=/tmp/.skins
rm -rf /usr/share/enigma2/Prime-NFR-Style-by-stein17
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

pkg_preinst:${PN} () {
#!/bin/sh                                                       
rm -rf /usr/share/enigma2/Prime-NFR-Style-by-stein17
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

pkg_prerm:${PN} () {
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
