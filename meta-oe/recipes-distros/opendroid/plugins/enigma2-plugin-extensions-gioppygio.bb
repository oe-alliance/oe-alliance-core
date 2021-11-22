DESCRIPTION = "enigma2-plugins-GioppyGio"
MAINTAINER = "opendroid-Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "7.1+git${SRCPV}"
PKGV = "7.1+git${GITPKGV}"
VER ="7.1"
PR = "r1"

SRC_URI="git://github.com/opendroid-Team/gioppygio-settings-and-picons.git;protocol=https"

S = "${WORKDIR}/git"

FILES_${PN} = "${libdir}"

do_install() {
    install -d ${D}${libdir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
}

pkg_postinst_${PN} () {
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

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/GioppyGio
echo "
echo "Plugin removed! You should restart enigma2 now!"
echo "
exit 0
}

