MODULE = "XionHDF"
SUMMARY = "HDFreaks.cc XionHDF"
MAINTAINER = "HDFreaks"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
VER ="1.0"
PR = "r6"

SRC_URI="git://github.com/KravenHD/XionHDF.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/share ${libdir}"

do_install() {
	install -d ${D}${libdir}
	install -d ${D}/usr/share
        cp -rp ${S}/usr/lib/* ${D}/${libdir}
	cp -rp ${S}/usr/share/* ${D}/usr/share
}

pkg_preinst_${PN}() {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/XionHDF/skin.xml ]; then
    cp -R /usr/share/enigma2/XionHDF/ /tmp
    rm -rf /usr/share/enigma2/XionHDF
    rm -rf /usr/${libdir}/enigma2/python/Components/Converter/XionHDF*
    rm -rf /usr/${libdir}/enigma2/python/Components/Renderer/XionHDF*
		echo "                                                           "
		echo "             Previous XionHDF installation                 "
		echo "                 was found and removed!                    "
		echo "                                                           "
fi
if [ -f /usr/${libdir}/enigma2/python/Plugins/Extensions/XionHDF/plugin.py ]; then
    rm -rf /usr/${libdir}/enigma2/python/Plugins/Extensions/XionHDF
		echo "                                                           "
		echo "              XionHDF configuration plugin                 "
		echo "                 was found and removed!                    "
		echo "                                                           "
fi




echo "                                                           "
echo "         The Skin XionHDF is now being installed...        "
echo "                                                           "
exit 0
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -f /tmp/XionHDF/skin.xml ]; then
	cp -R /usr/share/enigma2/XionHDF/buttonsets/ /tmp/XionHDF/
	rm -rf /usr/share/enigma2/XionHDF/
	mv /tmp/XionHDF /usr/share/enigma2/
fi
echo "                                                          "
echo "             ...Skin successful installed.                "
echo "                                                          "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "         The Skin XionHDF is now being removed...          "
echo "                                                           "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/XionHDF
rm -rf /usr/${libdir}/enigma2/python/Plugins/Extensions/XionHDF
rm -rf /usr/${libdir}/enigma2/python/Components/Converter/XionHDF*
rm -rf /usr/${libdir}/enigma2/python/Components/Renderer/XionHDF*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
exit 0
}
