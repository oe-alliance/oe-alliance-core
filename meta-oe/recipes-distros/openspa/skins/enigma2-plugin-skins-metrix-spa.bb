SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "http://open-store.net "
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv gettext
DEPENDS += "gettext-native"

SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r0"

PACKAGES =+ "enigma2-plugin-skins-metrix-spa-fhd-icons enigma2-plugin-skins-metrix-spa-uhd-icons"
PROVIDES =+ "enigma2-plugin-skins-metrix-spa-fhd-icons enigma2-plugin-skins-metrix-spa-uhd-icons"
RPROVIDES:enigma2-plugin-skins-metrix-spa-fhd-icons += "enigma2-plugin-skins-metrix-spa-fhd-icons"
RPROVIDES:enigma2-plugin-skins-metrix-spa-uhd-icons += "enigma2-plugin-skins-metrix-spa-uhd-icons"

SRC_URI="git://github.com/openspa/MetrixHD.git;protocol=https;branch=py3"

S = "${WORKDIR}/git"

FILES:enigma2-plugin-skins-metrix-spa-fhd-icons = "/usr/share/enigma2/MetrixHD/FHD"
FILES:enigma2-plugin-skins-metrix-spa-uhd-icons = "/usr/share/enigma2/MetrixHD/UHD"


FILES:${PN} = "${libdir} /usr/share ${sysconfdir}"

do_compile() {
	for f in $(find ${S}/locale -name *.po ); do
		l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
		mkdir -p ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES
		msgfmt -o ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES/MyMetrixLite.mo ${S}/locale/$l.po
	done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    install -d ${D}/etc/enigma2
    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}${libdir}/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
    cp -r --preserve=mode,links ${S}/etc/enigma2/* ${D}/etc/enigma2/
}

pkg_preinst:${PN}() {
#!/bin/sh
echo "Checking for skin.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin.MySkin.xml
    echo "skin.MySkin.xml was found and removed"
else
    echo "skin.MySkin.xml was not found in the skinfolder"
fi
echo "Checking for skin_00a_InfoBar.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin_00a_InfoBar.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin_00a_InfoBar.MySkin.xml
    echo "skin_00a_InfoBar.MySkin.xml was found and removed"
else
    echo "skin_00a_InfoBar.MySkin.xml was not found in the skinfolder"
fi
echo "Checking for skin_00b_SecondInfoBar.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin_00b_SecondInfoBar.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin_00b_SecondInfoBar.MySkin.xml
    echo "skin_00b_SecondInfoBar.MySkin.xml was found and removed"
else
    echo "skin_00b_SecondInfoBar.MySkin.xml was not found in the skinfolder"
fi
echo "Checking for skin_00c_SecondInfoBarECM.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin_00c_SecondInfoBarECM.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin_00c_SecondInfoBarECM.MySkin.xml
    echo "skin_00c_SecondInfoBarECM.MySkin.xml was found and removed"
else
    echo "skin_00c_SecondInfoBarECM.MySkin.xml was not found in the skinfolder"
fi
echo "Checking for skin_00d_InfoBarLite.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin_00d_InfoBarLite.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin_00d_InfoBarLite.MySkin.xml
    echo "skin_00d_InfoBarLite.MySkin.xml was found and removed"
else
    echo "skin_00d_InfoBarLite.MySkin.xml was not found in the skinfolder"
fi
echo "Checking for skin_00e_ChannelSelection.MySkin.xml in the skinfolder"
if [ -f /usr/share/enigma2/MetrixHD/skin_00e_ChannelSelection.MySkin.xml ]; then
    rm -f /usr/share/enigma2/MetrixHD/skin_00e_ChannelSelection.MySkin.xml
    echo "skin_00e_ChannelSelection.MySkin.xml was found and removed"
else
    echo "skin_00e_ChannelSelection.MySkin.xml was not found in the skinfolder"
fi
echo "Proceeding to installation..."
exit 0
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "Checking for obsolete MyMetrixLiteColors"
if [ -d ${libdir}/enigma2/python/Plugins/Extensions/MyMetrixLiteColors ]; then
    rm -rf ${libdir}/enigma2/python/Plugins/Extensions/MyMetrixLiteColors 2>/dev/null
    echo "MyMetrixLiteColors was found and removed"
else
    echo "MyMetrixLiteColors was not found"
fi
echo "Proceeding..."
exit 0
}
