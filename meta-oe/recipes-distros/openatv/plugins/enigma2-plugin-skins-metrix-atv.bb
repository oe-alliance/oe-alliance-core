SUMMARY = "Enigma2 Skin Metrix HD"
MAINTAINER = "https://opena.tv"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv gettext
DEPENDS += "gettext-native"
RDEPENDS_${PN} = "${@bb.utils.contains_any("FLASHSIZE", "64 96 128", "", "enigma2-plugin-skins-metrix-atv-weather-icons", d)}"

SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER ="3.0"
PR = "r0"

PACKAGES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons enigma2-plugin-skins-metrix-atv-weather-icons"
PROVIDES =+ "enigma2-plugin-skins-metrix-atv-fhd-icons enigma2-plugin-skins-metrix-atv-uhd-icons enigma2-plugin-skins-metrix-atv-weather-icons"
RPROVIDES_enigma2-plugin-skins-metrix-atv-fhd-icons += "enigma2-plugin-skins-metrix-atv-fhd-icons"
RPROVIDES_enigma2-plugin-skins-metrix-atv-uhd-icons += "enigma2-plugin-skins-metrix-atv-uhd-icons"
RPROVIDES_enigma2-plugin-skins-metrix-atv-weather-icons += "enigma2-plugin-skins-metrix-atv-weather-icons"
SRC_URI="git://github.com/openatv/MetrixHD.git;protocol=https"

S = "${WORKDIR}/git"

FILES_enigma2-plugin-skins-metrix-atv-fhd-icons = "/usr/share/enigma2/MetrixHD/FHD"
FILES_enigma2-plugin-skins-metrix-atv-uhd-icons = "/usr/share/enigma2/MetrixHD/UHD"
FILES_enigma2-plugin-skins-metrix-atv-weather-icons = "/usr/share/enigma2/MetrixHD/animated_weather_icons"

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
