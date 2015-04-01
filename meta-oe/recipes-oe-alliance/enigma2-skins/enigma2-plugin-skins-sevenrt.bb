SUMMARY = "SevenRT Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven and oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.4+git${SRCPV}"
PKGV = "1.4+git${GITPKGV}"
VER="1.4"

SRC_URI="git://github.com/KravenHD/SevenRT.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git/data"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
cd /usr/share/enigma2/SevenRT/
echo " "
echo " ...Skin successful installed. "
echo " "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/SevenRT
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenRT
rm -rf /usr/lib/enigma2/python/Components/Converter/SevenRT*
rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenRT*
echo " "
echo " ...Skin successful removed. "
echo " "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/SevenRT/skin.xml ]; then
	rm -rf /usr/share/enigma2/SevenRT
	rm -rf /usr/lib/enigma2/python/Components/Converter/SevenRT*
	rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenRT*
		echo " "
		echo " Previous SevenRT installation "
		echo " was found and removed! "
		echo " "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/SevenRT/plugin.py ]; then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenRT
		echo " "
		echo " SevenRT configuration plugin "
		echo " was found and removed! "
		echo " "
fi
	echo " "
	echo " The Skin SevenRT is now being installed... "
	echo " "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin SevenRT is now being removed... "
echo " "
exit 0
}
