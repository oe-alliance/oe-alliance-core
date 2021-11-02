SUMMARY = "Skin Full HD for NFR Images by stein17"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "3.2+git${SRCPV}"
PKGV = "3.2+git${GITPKGV}"
VER="3.2"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openNFR.git;protocol=https"

FILES_${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git/AX-Blue-FHD-4NFR"

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
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/AX_Blue_FHD_4NFR
rm -rf /usr/share/enigma2/Spinner/AX_Blue
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "        AX-Blue-FHD Skin will be now installed...            "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "              AX-Blue-FHD is now being removed...          "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"
