SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "http://open-store.net "
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r3"

SRC_URI="git://github.com/openatv/MetrixHD.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
    cp -rp ${S}/usr ${D}/
}

pkg_preinst_${PN}() {
#!/bin/sh
echo "Checking for skin.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin.MySkin.xml
    echo "skin.MySkin.xml was found and removed"
else
    echo "skin.MySkin.xml was not found in the skinfolder"
fi
echo "Proceeding to installation..."
exit 0
}
