DESCRIPTION = "enigma2-plugin-extensions-GioppyGio"
MAINTAINER = "opendroid-Team"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.0+git${SRCPV}"
PKGV = "2.0+git${GITPKGV}"
VER ="2.0"
PR = "r0"

SRC_URI="git://github.com/opendroid-Team/gioppygio-settings-and-picons.git"

S = "${WORKDIR}/git"

FILES_${PN} = "/usr/*"

do_install() {
	cp -rp ${S}/usr ${D}/
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
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/GioppyGio/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}
