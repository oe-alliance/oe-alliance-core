SUMMARY = "Full HD Skin for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc
require conf/python/${PYTHON_PN}-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.1+git"
PKGV = "1.1+git${GITPKGV}"
VER="1.1"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https;branch=Python3"

FILES:${PN} = "${libdir} ${datadir}"

S = "${WORKDIR}/git/Multibox-FHD-Skin-4HDF"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/${datadir}/* ${D}${datadir}
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo ""
echo "...Multibox Skin by stein17 successful installed."
echo ""
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /enigma2/Multibox
rm -rf /usr/lib/enigma2/python/Components/Converter/AMB*
rm -rf /usr/lib/enigma2/python/Components/Renderer/AMB*
echo ""
echo "              ...Skin successful removed."
echo ""
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh
echo ""
echo "Multibox Skin Full HD by stein17 is now being installed..."
echo ""
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo ""
echo "Multibox Skin Full HD by stein17 is now being removed..."
echo ""
exit 0
}

do_package_qa[noexec] = "1"
