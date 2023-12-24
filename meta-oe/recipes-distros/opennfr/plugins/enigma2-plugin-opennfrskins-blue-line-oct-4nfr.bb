SUMMARY = "Skin Full HD for NFR Images by stein17"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r3"

SRC_URI="git://github.com/stein17/Skins-for-openNFR.git;protocol=https;branch=6.5"

S = "${WORKDIR}/git/Blue-Line-OCT-4NFR"

FILES:${PN} = "/tmp ${libdir} /usr/share"

do_install() {
	install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo " Skin Blue-Line-OCT-4NFR installed "
exit 0
}

pkg_postrm:${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OCT-4NFR/
echo ""
echo ""
echo "Skin removed! You should restart GUI now!"
echo ""
exit 0
}

pkg_preinst:${PN} () {
#!/bin/sh

${PYTHON_PN} ${libdir}/enigma2/python/BoxBrandingTest.pyo | sed 's/<$//' | sed 's/ /_/g' > /tmp/boxbranding.cfg

exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                           "
echo "              AX-Blue-FHD is now being removed...          "
echo "                                                           "
exit 0
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
