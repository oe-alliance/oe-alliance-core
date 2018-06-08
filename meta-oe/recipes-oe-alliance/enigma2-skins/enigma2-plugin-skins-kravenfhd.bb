SUMMARY = "KravenFHD Skin for Enigma2 by Kraven Team"
MAINTAINER = "Kraven Team"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "3.6+git${SRCPV}"
PKGV = "3.6+git${GITPKGV}"
VER="3.6"

RDEPENDS_${PN} = "python-imaging python-subprocess python-requests python-lxml enigma2-plugin-systemplugins-mphelp"

SRC_URI="git://github.com/KravenHD/KravenFHD.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -d /tmp/graphics ]; then
    mv -f /tmp/graphics/*.* /usr/share/enigma2/KravenFHD/graphics
fi
if [ -f /tmp/skin-user.xml ]; then
    mv -f /tmp/skin-user.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/plugin.py ]; then
    wget -O /dev/null 'http://127.0.0.1/web/message?type=1&text=KravenFHD%20wurde%20erfolgreich%20installiert.%0A%0AZur%20Nutzung%20rufen%20Sie%20das%20Plugin%20auf,%20speichern%20Ihre%20Einstellungen%0Aund%20starten%20die%20Oberfl%C3%A4che%20neu.&timeout=13'
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
echo "Checking for previous installations..."
if [ -d /usr/share/enigma2/KravenFHD/graphics ]; then
    mkdir /tmp/graphics
    cp /usr/share/enigma2/KravenFHD/graphics/*.* /tmp/graphics
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenFHD/data/skin-user.xml /tmp
fi
if [ -f /usr/share/enigma2/KravenFHD/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenFHD/skin.xml
fi
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
exit 0
}

do_package_qa[noexec] = "1"

