SUMMARY = "KravenFHD Skin for Enigma2 by Kraven Team"
MAINTAINER = "Kraven Team"
require conf/license/license-gplv2.inc

DEPENDS += "gettext-native"
RDEPENDS_${PN} += "python-imaging python-subprocess python-requests python-lxml enigma2-plugin-systemplugins-mphelp"

inherit gitpkgv allarch gettext

SRCREV = "${AUTOREV}"
PV = "7.x+git${SRCPV}"
PKGV = "7.x+git${GITPKGV}"
VER="7.x"

SRC_URI="git://github.com/atvcaptain/KravenFHD.git;protocol=https"

FILES_${PN} = "/usr/*"
FILES_${PN}-src = "\
    /usr/lib/enigma2/python/Components/Converter/*.py \
    /usr/lib/enigma2/python/Components/Renderer/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/*/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/*/*/*.py \
    /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/locale/*/LC_MESSAGES/*.po \
    "

S = "${WORKDIR}/git"

do_compile() {
    python -O -m compileall ${S}/usr
    for f in $(find ${S}/locale -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
        #mkdir -p ${S}/usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/locale/${l%}/LC_MESSAGES
        #msgfmt -o ${S}/usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/locale/${l%}/LC_MESSAGES/KravenFHD.mo ${S}/usr/lib/enigma2$
    done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -r --preserve=mode,links ${S}/usr/lib/* ${D}/usr/lib/
    cp -r --preserve=mode,links ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f /tmp/kravenfhdskin ]; then
    mv -f /tmp/kravenfhdskin /usr/share/enigma2/KravenFHD/skin.xml
fi
if [ -d /tmp/graphicsfhd ]; then
    mv -f /tmp/graphicsfhd/* /usr/share/enigma2/KravenFHD/graphics/
fi
if [ -f /tmp/fhd-skin-user.xml ]; then
    mv -f /tmp/fhd-skin-user.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data/skin-user.xml
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/plugin.py* ]; then
    wget -q -O /dev/null 'http://127.0.0.1/web/message?type=1&text=KravenFHD%20wurde%20erfolgreich%20installiert.%0A%0AZur%20Nutzung%20rufen%20Sie%20das%20Plugin%20auf,%20speichern%20Ihre%20Einstellungen%0Aund%20starten%20die%20Oberfl%C3%A4che%20neu.&timeout=13' || true
fi
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "               .########..##.....##..######....            "
echo "               .##........##.....##..##....##..            "
echo "               .##........##.....##..##.....##.            "
echo "               .######....#########..##.....##.            "
echo "               .##........##.....##..##.....##.            "
echo "               .##........##.....##..##....##..            "
echo "               .##........##.....##..######....            "
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenFHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenFHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenFHD*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "               .########..##.....##..######....            "
echo "               .##........##.....##..##....##..            "
echo "               .##........##.....##..##.....##.            "
echo "               .######....#########..##.....##.            "
echo "               .##........##.....##..##.....##.            "
echo "               .##........##.....##..##....##..            "
echo "               .##........##.....##..######....            "
echo "                                                           "
echo "              ...Skin successful removed.                  "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenFHD is now being installed...       "
echo "                                                           "
exit 0                                                           
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenFHD is now being removed...         "
echo "                                                           "
if [ -e /usr/share/enigma2/KravenFHD/skin.xml ]; then
    cp /usr/share/enigma2/KravenFHD/skin.xml /tmp/kravenfhdskin
fi
if [ -d /usr/share/enigma2/KravenFHD/graphics ]; then
    mkdir /tmp/graphicsfhd
    cp /usr/share/enigma2/KravenFHD/graphics/* /tmp/graphicsfhd/
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data/skin-user.xml /tmp/fhd-skin-user.xml
fi
if [ -f /usr/share/enigma2/KravenFHD/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenFHD/skin.xml
fi
exit 0
}

do_package_qa[noexec] = "1"
