SUMMARY = "SevenHD Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven and oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "3.1+git${SRCPV}"
PKGV = "3.1+git${GITPKGV}"
VER="3.1"

SRC_URI="git://github.com/KravenHD/SevenHD.git;protocol=git"

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
rm -rf /usr/share/enigma2/SevenHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenHD
rm -rf /usr/lib/enigma2/python/Components/Converter/SevenHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenHD*
echo " "
echo " ...Skin successful removed. "
echo " "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/SevenHD/skin.xml ]; then
	rm -rf /usr/share/enigma2/SevenHD
	rm -rf /usr/lib/enigma2/python/Components/Converter/SevenHD*
	rm -rf /usr/lib/enigma2/python/Components/Renderer/SevenHD*
	echo " "
	echo " Previous SevenHD installation "
	echo " was found and removed! "
	echo " "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/SevenHD/plugin.py ]; then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SevenHD
	echo " "
	echo " SevenHD configuration plugin "
	echo " was found and removed! "
	echo " "
fi
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin SevenHD is now being removed... "
echo " "
exit 0
}
