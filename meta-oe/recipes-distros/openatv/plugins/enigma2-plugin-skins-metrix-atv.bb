SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "https://opena.tv"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv gettext
DEPENDS += "gettext-native"
RDEPENDS:${PN} = "enigma2-tools-weatherinfo ${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "enigma2-plugin-skins-metrix-atv-weather-icons", d)}"

SRCREV = "${AUTOREV}"
PV = "${DISTRO_VERSION}+git${SRCPV}"
PKGV = "${DISTRO_VERSION}+git${GITPKGV}"
VER ="${DISTRO_VERSION}"
PR = "r1"

PACKAGES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons enigma2-plugin-skins-metrix-atv-weather-icons"
PROVIDES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons enigma2-plugin-skins-metrix-atv-weather-icons"
RPROVIDES:enigma2-plugin-skins-metrix-atv-fhd-icons += "enigma2-plugin-skins-metrix-atv-fhd-icons"
RPROVIDES:enigma2-plugin-skins-metrix-atv-uhd-icons += "enigma2-plugin-skins-metrix-atv-uhd-icons"
RPROVIDES:enigma2-plugin-skins-metrix-atv-weather-icons += "enigma2-plugin-skins-metrix-atv-weather-icons"
SRC_URI="git://github.com/openatv/MetrixHD.git;branch=master;protocol=https"

S = "${WORKDIR}/git"

FILES:enigma2-plugin-skins-metrix-atv-fhd-icons = "/usr/share/enigma2/MetrixHD/FHD"
FILES:enigma2-plugin-skins-metrix-atv-uhd-icons = "/usr/share/enigma2/MetrixHD/UHD"
FILES:enigma2-plugin-skins-metrix-atv-weather-icons = "/usr/share/enigma2/MetrixHD/animated_weather_icons"


FILES:${PN} = "${libdir} /usr/share"

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
    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}${libdir}/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
}

pkg_preinst:${PN}() {
#!/bin/sh
echo "remove symlinks ..."
for f in /usr/share/enigma2/MetrixHD/*
do
if [ -L $f ]; then
    unlink $f
fi
done
echo "... done"
echo "remove mySkin ..."
rm -f /usr/share/enigma2/MetrixHD/skinfiles/*.mySkin.xml
rm -f /usr/share/enigma2/MetrixHD/skin.MySkin.xml
echo "... done"
echo "restore skin ..."
if [ -r /usr/share/enigma2/MetrixHD/skin.xml_original_file_.xml ]; then
    mv /usr/share/enigma2/MetrixHD/skin.xml_original_file_.xml /usr/share/enigma2/MetrixHD/skin.xml
fi
for f in /usr/share/enigma2/MetrixHD/.*_hd;
do
if [ -r $f ]; then
    mv "$f" "$(echo ${f%} | sed 's/\.//' | sed 's/_hd//')"
fi
done
echo "... done"
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
