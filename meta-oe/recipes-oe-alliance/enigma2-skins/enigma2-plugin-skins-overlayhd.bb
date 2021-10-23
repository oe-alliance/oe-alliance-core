DESCRIPTION = "OverlayHD skin and management plugin for Enigma2 PVRs by IanSav"
SECTION = "skins"
PRIORITY = "optional"
MAINTAINER = "IanSav <IS.OzPVR@gmail.com>"
HOMEPAGE = "https://github.com/IanSav"

require conf/license/license-gplv2.inc

inherit allarch gitpkgv

SRCREV = "${AUTOREV}"
PV = "1.90-git${SRCPV}"
PKGV = "1.90-git${GITPKGV}"
SRC_URI = "git://github.com/IanSav/OverlayHD.git;protocol=https"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir} ${datadir}"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rf ${S}/usr/lib/* ${D}${libdir}/
    cp -rf ${S}/usr/share/* ${D}${datadir}/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo ""
echo "+--------------------------------------------------+"
echo "| OverlayHD skin successfully installed / updated. |"
echo "+--------------------------------------------------+"
echo "| Skin developed by IanSav.                        |"
echo "| https://github.com/IanSav/overlayhd              |"
echo "+--------------------------------------------------+"
echo ""
echo "Restart the GUI to enable changes.  Failure to restart"
echo "could result in a GUI crash!"
echo ""
echo "Use the Skin Setup menu to activate the OverlayHD"
echo "skin.  The OverlayHD Skin Manager plugin will become"
echo "active when the OverlayHD skin is active."
echo ""
exit 0
}
