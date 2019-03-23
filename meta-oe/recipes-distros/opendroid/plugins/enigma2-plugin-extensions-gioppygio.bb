DESCRIPTION = "enigma2-plugin-extensions-GioppyGio"
MAINTAINER = "opendroid-Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.2+git${SRCPV}"
PKGV = "2.2+git${GITPKGV}"
VER ="2.2"
PR = "r3"

SRC_URI="git://github.com/opendroid-Team/gioppygio-settings-and-picons.git"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

pkg_postinst_${PN}() {
#!/bin/sh
echo ""
echo ""
echo "****************************************"
echo "* plugin-gioppygio-settings-and-Picons *"
echo "*               by                     *"
echo "*            GioppyGio                 *"
echo "****************************************"
echo ""
echo ""
echo "Plugin successfully installed!"
echo ""
echo "You should restart enigma2 now..."
echo ""
echo ""
echo ""
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm ${libdir}/lib/enigma2/python/Plugins/Extensions/GioppyGio/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}
