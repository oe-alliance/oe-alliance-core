SUMMARY = "KravenSE Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven and oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.6+git${SRCPV}"
PKGV = "1.6+git${GITPKGV}"
VER="1.6"

SRC_URI="git://github.com/KravenHD/KravenSE.git;protocol=git"

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
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .#########.########.                   "
echo "                    .##........##.......                   "
echo "                    .##........##.......                   "
echo "                    .#########.########.                   "
echo "                    ........##.##.......                   "
echo "                    ........##.##.......                   "
echo "                    .#########.########.                   "
echo "                                                           "
echo "              ...Skin successful installed...              "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenSE
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenSE
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenSE*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenSE*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .#########.########.                   "
echo "                    .##........##.......                   "
echo "                    .##........##.......                   "
echo "                    .#########.########.                   "
echo "                    ........##.##.......                   "
echo "                    ........##.##.......                   "
echo "                    .#########.########.                   "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/KravenSE/skin.xml ]; then
    rm -rf /usr/share/enigma2/KravenSE
    rm -rf /usr/lib/enigma2/python/Components/Converter/KravenSE*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenSE*
		echo "                                                           "
		echo "          Previous KravenSE installation                   "
		echo "                 was found and removed!                    "
		echo "                                                           "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/KravenSE/plugin.py ]; then
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenSE
		echo "                                                           "
		echo "           KravenSE configuration plugin                   "
		echo "                 was found and removed!                    "
		echo "                                                           "
fi




echo "                                                           "
echo "         The Skin KravenSE is now being installed...        "
echo "                                                           "
exit 0
}
