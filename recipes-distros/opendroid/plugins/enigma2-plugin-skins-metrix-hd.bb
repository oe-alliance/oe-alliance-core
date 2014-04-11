SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "opendroid"
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
PR = "r5"

SRC_URI="git://github.com/opendroid-Team/MetrixHD.git"

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
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLiteColors ]; then
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/MyMetrixLiteColors 2>/dev/null
    echo "MyMetrixLiteColors was found and removed"
else
    echo "MyMetrixLiteColors was not found"
fi
echo "Proceeding..."
exit 0
}