SUMMARY = "Full HD Skin for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv 

SRCREV = "${AUTOREV}"
PV = "1.8+git"
PKGV = "1.8+git${GITPKGV}"
VER="1.8"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=https;branch=python3"

FILES:${PN} = "/"



S = "${WORKDIR}/git/Blue-Line-OE-4ATV"


do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...Blue-Line-OE-4ATV by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OE-4ATV
rm -rf /usr/lib/enigma2/python/Components/Converter/BL*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL*
echo "                                                          "
echo " ....Skin removed! You should restart GUI now!                 "
echo "
}

pkg_preinst:${PN} () {
#!/bin/sh                                                       
rm -rf /usr/share/enigma2/Blue-Line-OE-4ATV
rm -rf /usr/lib/enigma2/python/Components/Converter/BL
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL
echo "aktualisiere Updatequellen... "opkg update 
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "                                                                           "
echo "  Blue-Line-OE-4ATV by stein17 is now being installed...                 "
echo "                                                                           " 
echo "                                                                           "
echo "                                                                           "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                           "
echo "              Skin is now being removed...                 "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"

