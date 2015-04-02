SUMMARY = "SevenFullHD Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven and oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"
VER="1.4"

SRC_URI="git://github.com/KravenHD/SevenFullHD.git;protocol=git"

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
echo " "
echo " ...Skin successful installed. "
echo " "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/SevenFullHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenFullHD
rm -rf /usr/lib/enigma2/python/Components/Converter/SevenFullHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenFullHD*
echo " "
echo " ...Skin successful removed. "
echo " "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/SevenFullHD/skin.xml ]; then
	rm -rf /usr/share/enigma2/SevenFullHD
	rm -rf /usr/lib/enigma2/python/Components/Converter/SevenFullHD*
	rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenFullHD*
	echo " "
	echo " Previous SevenFullHD installation "
	echo " was found and removed! "
	echo " "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/SevenFullHD/plugin.py ]; then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenFullHD
	echo " "
	echo " SevenFullHD configuration plugin "
	echo " was found and removed! "
	echo " "
else
    echo "SevenFullHD Plugin was not found"
fi
echo " "
echo " The Skin SevenFullHD is now being installed... "
echo " "

exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin SevenFullHD is now being removed... "
echo " "
exit 0
}
