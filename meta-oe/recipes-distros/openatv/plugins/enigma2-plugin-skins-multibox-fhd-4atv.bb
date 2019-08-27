SUMMARY = "Skin Multibox FHD for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openATV.git;protocol=git"

FILES_${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git/Multibox-FHD-Skin-4ATV"

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
echo " ...Multibox Skin Full HD by stein17 successful installed.  "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Multibox
rm -rf /usr/lib/enigma2/python/Components/Converter/AMB*
rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                                           "
echo "  Multibox Skin Full HD by stein17 is now being installed...               "
echo "                                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo " Multibox Skin Full HD by stein17 is now being removed...    "
echo "                                                           "
}

do_package_qa[noexec] = "1"

