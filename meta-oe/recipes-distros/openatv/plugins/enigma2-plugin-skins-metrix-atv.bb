SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "http://open-store.net "
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv gettext
DEPENDS += "gettext-native"

SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r7"

PACKAGES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons ${PN}-src"
PROVIDES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons"
RPROVIDES_enigma2-plugin-skins-metrix-atv-fhd-icons += "enigma2-plugin-skins-metrix-atv-fhd-icons"
RPROVIDES_enigma2-plugin-skins-metrix-atv-uhd-icons += "enigma2-plugin-skins-metrix-atv-uhd-icons"
SRC_URI="git://github.com/openatv/MetrixHD.git"

S = "${WORKDIR}/git"

FILES_enigma2-plugin-skins-metrix-atv-fhd-icons = "/usr/share/enigma2/MetrixHD/FHD"
FILES_enigma2-plugin-skins-metrix-atv-uhd-icons = "/usr/share/enigma2/MetrixHD/UHD"
FILES_${PN}-src = "\
    ${libdir}/enigma2/python/Components/Converter/*.py \
    ${libdir}/enigma2/python/Components/Renderer/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/MyMetrixLite/*.py \
    "

FILES_${PN} = "${libdir} /usr/share"

do_compile() {
	python -O -m compileall ${S}/usr
	for f in $(find ${S}/locale -name *.po ); do
		l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
		mkdir -p ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES
		msgfmt -o ${S}/usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLite/locale/${l%}/LC_MESSAGES/MyMetrixLite.mo ${S}/locale/$l.po
	done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}${libdir}/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
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

pkg_postinst_${PN} () {
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
