SUMMARY = "KravenVB Skin for Enigma2 by Kraven Team"
MAINTAINER = "Kraven Team"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "6.6+git${SRCPV}"
PKGV = "6.6+git${GITPKGV}"
VER="6.6"

RDEPENDS_${PN} = "python-imaging python-subprocess python-requests python-lxml enigma2-plugin-systemplugins-mphelp"

SRC_URI="git://github.com/KravenHD/KravenVB.git;protocol=git"

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
    mv -f /tmp/graphics/*.* /usr/share/enigma2/KravenVB/graphics
fi
if [ -f /tmp/skin-user.xml ]; then
    mv -f /tmp/skin-user.xml /usr/lib/enigma2/python/Plugins/Extensions/KravenVB/data
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenVB/plugin.py ]; then
    wget -O /dev/null 'http://127.0.0.1/web/message?type=1&text=KravenVB%20wurde%20erfolgreich%20installiert.%0A%0AZur%20Nutzung%20rufen%20Sie%20das%20Plugin%20auf,%20speichern%20Ihre%20Einstellungen%0Aund%20starten%20die%20Oberfl%C3%A4che%20neu.&timeout=13'
fi
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##..########..                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..########..                 "
echo "                    ..##...##...##.....##.                 "
echo "                    ...##.##....##.....##.                 "
echo "                    ....###.....########..                 "
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenVB
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenVB
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenVB*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenVB*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##..########..                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..##.....##.                 "
echo "                    .##.....##..########..                 "
echo "                    ..##...##...##.....##.                 "
echo "                    ...##.##....##.....##.                 "
echo "                    ....###.....########..                 "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -d /usr/share/enigma2/KravenVB/graphics ]; then
    mkdir /tmp/graphics
    cp /usr/share/enigma2/KravenVB/graphics/*.* /tmp/graphics
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenVB/data/skin-user.xml ]; then
    cp /usr/lib/enigma2/python/Plugins/Extensions/KravenVB/data/skin-user.xml /tmp
fi
if [ -f /usr/share/enigma2/KravenVB/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenVB/skin.xml
fi
echo "KravenVB Skin will be now installed..."
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "        The Skin KravenVB is now being removed...          "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"

