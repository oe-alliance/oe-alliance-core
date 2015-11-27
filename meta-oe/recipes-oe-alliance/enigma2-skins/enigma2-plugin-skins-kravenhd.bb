SUMMARY = "KravenHD Skin for Enigma2 by Kraven"
MAINTAINER = "Kraven"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "6.7.0+git${SRCPV}"
PKGV = "6.7.0+git${GITPKGV}"
VER="6.7.0"

SRC_URI="git://github.com/KravenHD/KravenHD.git;protocol=git"

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
echo "                    .##.....##.########.                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .#########.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.########.                   "
echo "                                                           "
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/KravenHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenHD
rm -rf /usr/lib/enigma2/python/Components/Converter/KravenHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/KravenHD*
echo " .##....##.########.....###....##.....##.########.##....## "
echo " .##...##..##.....##...##.##...##.....##.##.......###...## "
echo " .##..##...##.....##..##...##..##.....##.##.......####..## "
echo " .#####....########..##.....##.##.....##.######...##.##.## "
echo " .##..##...##...##...#########..##...##..##.......##..#### "
echo " .##...##..##....##..##.....##...##.##...##.......##...### "
echo " .##....##.##.....##.##.....##....###....########.##....## "
echo " .........................................................."
echo "                    .##.....##.########.                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .#########.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.##.....##                   "
echo "                    .##.....##.########.                   "
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for obsolete KravenHD Plugin"
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/KravenHD ]; then
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/KravenHD 2>/dev/null
    echo "KravenHD Plugin was found and removed"
else
    echo "KravenHD Plugin was not found"
fi
echo "KravenHD Skin will be now installed..."
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "KravenHD Skin will be now removed..."
exit 0
}
