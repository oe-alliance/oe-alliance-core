SUMMARY = "Plugin for updating Chocholousek's picons in Enigma2"
DESCRIPTION = "Enigma2 plugin for downloading and updating picons (almost all EU satellites)"
HOMEPAGE = "https://github.com/s3n0/e2plugins/ChocholousekPicons"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "p7zip"

inherit gitpkgv gettext

SRCREV = "${AUTOREV}"
BPV = "5.0"
PV = "${BPV}+git${SRCPV}"
PKGV = "${BPV}+git${GITPKGV}"
PR = "r1"

SRC_URI = "git://github.com/s3n0/e2plugins;protocol=https;branch=master"

S = "${WORKDIR}/git/ChocholousekPicons/src"

do_compile() {
    # generate translation .mo files
    find ${S}/locale -name \*.po -execdir sh -c 'msgfmt "$0" -o `basename $0 .po`.mo' '{}' \;
}

PACKAGES =+ "${PN}-po"
FILES:${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/ChocholousekPicons/locale/*/*/*.po"
FILES:${PN} = "${libdir}/enigma2/python/Plugins/Extensions/ChocholousekPicons"
D_FILES_PN = "${D}${FILES:${PN}}"

do_install() {
    install -d ${D_FILES_PN}
    install -d ${D_FILES_PN}/images
    install -d ${D_FILES_PN}/locale

    install -m 644 ${S}/*.py *.log *.txt ${D_FILES_PN}
    install -m 644 ${S}/images/*.* ${D_FILES_PN}/images
    cp -rf ${S}/locale ${D_FILES_PN}
}

pkg_postinst:${PN}() {
#!/bin/sh
echo "*********************************************************"
echo "      Chocholousek Picons - plugin ver.${BPV}        "
echo "                Enigma2 plugin/extensions                "
echo "                   by s3n0 , 2018-2023                   "
echo "*********************************************************"
echo " Successfully INSTALLED. You should restart Enigma2 now. "
echo "*********************************************************"
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh
[ "$1" != "upgrade" ] || exit 0 > /dev/null 2>&1              # prevent the OE2.5+ based Enigma2 for deleting files when the package is "upgrading"
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ChocholousekPicons > /dev/null 2>&1
echo "*********************************************************"
echo "      Chocholousek Picons - plugin ver.${BPV}        "
echo "                Enigma2 plugin/extensions                "
echo "                   by s3n0 , 2018-2023                   "
echo "*********************************************************"
echo "  Successfully REMOVED. You should restart Enigma2 now.  "
echo "*********************************************************"
exit 0
}
