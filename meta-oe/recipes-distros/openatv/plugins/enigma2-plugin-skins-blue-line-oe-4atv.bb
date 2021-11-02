SUMMARY = "Full HD Skin for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv 

SRCREV = "${AUTOREV}"
PV = "1.8+git${SRCPV}"
PKGV = "1.8+git${GITPKGV}"
VER="1.8"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=https"

FILES_${PN} = "/"



S = "${WORKDIR}/git/Blue-Line-OE-4ATV"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "                                                          "
echo " ...Blue-Line-OE-4ATV by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OE-4ATV
rm -rf /usr/lib/enigma2/python/Components/Converter/BL*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL*
echo "                                                          "
echo " ....Skin removed! You should restart GUI now!                 "
echo "
}

pkg_preinst_${PN} () {
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

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "              Skin is now being removed...                 "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"

