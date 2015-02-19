SUMMARY = "KravenFullHD Skin for Enigma2 by Kraven and Oerlgrey"
MAINTAINER = "Kraven and Oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.7+git${SRCPV}"
PKGV = "1.7+git${GITPKGV}"
VER="1.7"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-extensions-weatherplugin enigma2-plugin-systemplugins-extnumberzap"

SRC_URI="git://github.com/KravenHD/KravenFullHD.git;protocol=git"

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
echo " .##....##.########.....###....##.....##.########.##....##."
echo " .##...##..##.....##...##.##...##.....##.##.......###...##."
echo " .##..##...##.....##..##...##..##.....##.##.......####..##."
echo " .#####....########..##.....##.##.....##.######...##.##.##."
echo " .##..##...##...##...#########..##...##..##.......##..####."
echo " .##...##..##....##..##.....##...##.##...##.......##...###."
echo " .##....##.##.....##.##.....##....###....########.##....##."
echo " .........................................................."
echo " .########.##.....##.##........##........##....##.#######.."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .#####....##.....##.##........##........########.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......#########.#########.########..##....##.#######.."
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenFullHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenFullHD
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenFullHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenFullHD*
echo " .##....##.########.....###....##.....##.########.##....##."
echo " .##...##..##.....##...##.##...##.....##.##.......###...##."
echo " .##..##...##.....##..##...##..##.....##.##.......####..##."
echo " .#####....########..##.....##.##.....##.######...##.##.##."
echo " .##..##...##...##...#########..##...##..##.......##..####."
echo " .##...##..##....##..##.....##...##.##...##.......##...###."
echo " .##....##.##.....##.##.....##....###....########.##....##."
echo " .........................................................."
echo " .########.##.....##.##........##........##....##.#######.."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .#####....##.....##.##........##........########.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......##.....##.##........##........##....##.##....##."
echo " .##.......#########.#########.########..##....##.#######.."
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for obsolete KravenFullHD Plugin"
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/KravenFullHD ]; then
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenFullHD 2>/dev/null
    echo "KravenFullHD Plugin was found and removed"
else
    echo "KravenFullHD Plugin was not found"
fi
echo "KravenFullHD Skin will be now installed..."
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "KravenFullHD Skin will be now removed..."
exit 0
}
