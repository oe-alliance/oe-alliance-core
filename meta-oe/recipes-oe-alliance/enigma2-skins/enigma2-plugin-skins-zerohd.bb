SUMMARY = "ZeroHD Skin for Enigma2 by Kraven and oerlgrey"
MAINTAINER = "Kraven and oerlgrey"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER="1.0"

SRC_URI="git://github.com/KravenHD/ZeroHD.git;protocol=git"

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
#!/bin/sh
cd /usr/share/enigma2/ZeroHD/
echo " "
echo " ...Skin successful installed. "
echo " "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/ZeroHD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ZeroHD
rm -rf /usr/lib/enigma2/python/Components/Converter/ZeroHD*
rm -rf /usr/lib/enigma2/python/Components/Renderer/ZeroHD*
echo " "
echo " ...Skin successful removed. "
echo " "
exit0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/ZeroHD/skin.xml ]; then
	rm -rf /usr/share/enigma2/ZeroHD
	rm -rf /usr/lib/enigma2/python/Components/Converter/ZeroHD*
	rm -rf /usr/lib/enigma2/python/Components/Renderer/ZeroHD*
		echo " "
		echo " Previous ZeroHD installation "
		echo " was found and removed! "
		echo " "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/ZeroHD/plugin.py ]; then
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/ZeroHD
		echo " "
		echo " ZeroHD configuration plugin "
		echo " was found and removed! "
		echo " "
fi
		echo " "
		echo " The Skin ZeroHD is now being installed... "
		echo " "
exit0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin ZeroHD is now being removed... "
echo " "
exit0
}
