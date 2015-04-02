SUMMARY = "ZeroFullHD Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"
VER="1.4"

SRC_URI="git://github.com/KravenHD/ZeroFullHD.git;protocol=git"

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
echo " "
echo " ...Skin successful installed. "
echo " "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/ZeroFullHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ZeroFullHD
rm -rf /usr/lib/enigma2/python/Components/Converter/ZeroFullHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/ZeroFullHD*
echo " "
echo " ...Skin successful removed. "
echo " "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/ZeroFullHD/skin.xml ]; then
	rm -rf /usr/share/enigma2/ZeroFullHD
	rm -rf /usr/lib/enigma2/python/Components/Converter/ZeroFullHD*
	rm -rf /usr/lib/enigma2/python/Components/Renderer/ZeroFullHD*
	echo " "
	echo " Previous ZeroFullHD installation "
	echo " was found and removed! "
	echo " "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/ZeroFullHD/plugin.py ]; then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ZeroFullHD
	echo " "
	echo " ZeroFullHD configuration plugin "
	echo " was found and removed! "
	echo " "
else
    echo "ZeroFullHD Plugin was not found"	
fi
echo " "
echo " The Skin ZeroFullHD is now being installed... "
echo " "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin ZeroFullHD is now being removed... "
echo " "
exit 0
}
