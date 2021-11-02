SUMMARY = "Plugin for updating Chocholousek's picons in Enigma2"
DESCRIPTION = "Enigma2 plugin for downloading and updating picons (almost all EU satellites)"
HOMEPAGE = "https://github.com/s3n0/e2plugins/ChocholousekPicons"
SECTION = "multimedia"
LICENSE = "GPLv2"

require conf/license/license-gplv2.inc

RDEPENDS_${PN} = "p7zip"

inherit gitpkgv gettext

SRCREV = "${AUTOREV}"
BPV = "4.0.210301"
PV = "${BPV}+gitr${SRCPV}"
PKGV = "${BPV}+gitr${SRCPV}"
PR = "r1"

SRC_URI = "git://github.com/s3n0/e2plugins;protocol=https"

S = "${WORKDIR}/git/ChocholousekPicons/src"

do_compile() {
    # generate translation .mo files
    find ${S}/locale -name \*.po -execdir sh -c 'msgfmt "$0" -o `basename $0 .po`.mo' '{}' \;
}

PACKAGES =+ "${PN}-po"
FILES_${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/ChocholousekPicons/locale/*/*/*.po"
FILES_${PN} = "${libdir}/enigma2/python/Plugins/Extensions/ChocholousekPicons"
D_FILES_PN = "${D}${FILES_${PN}}"

do_install() {
    install -d ${D_FILES_PN}
    install -d ${D_FILES_PN}/images
    install -d ${D_FILES_PN}/locale

    install -m 644 ${S}/*.py *.log *.txt ${D_FILES_PN}
    install -m 644 ${S}/images/*.* ${D_FILES_PN}/images
    cp -rf ${S}/locale ${D_FILES_PN}
}

pkg_postinst_${PN}() {
#!/bin/sh
echo "*********************************************************"
echo "      Chocholousek Picons - plugin ver.${BPV}        "
echo "                Enigma2 plugin/extensions                "
echo "                   by s3n0 , 2018-2021                   "
echo "*********************************************************"
echo " Successfully INSTALLED. You should restart Enigma2 now. "
echo "*********************************************************"
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
[ "$1" != "upgrade" ] || exit 0 > /dev/null 2>&1              # prevent the OE2.5+ based Enigma2 for deleting files when the package is "upgrading"
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ChocholousekPicons > /dev/null 2>&1
echo "*********************************************************"
echo "      Chocholousek Picons - plugin ver.${BPV}        "
echo "                Enigma2 plugin/extensions                "
echo "                   by s3n0 , 2018-2021                   "
echo "*********************************************************"
echo "  Successfully REMOVED. You should restart Enigma2 now.  "
echo "*********************************************************"
exit 0
}
