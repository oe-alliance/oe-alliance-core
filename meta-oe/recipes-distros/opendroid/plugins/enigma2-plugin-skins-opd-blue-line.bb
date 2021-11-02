SUMMARY = "Skin Full HD for openDroid Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.6+git${SRCPV}"
PKGV = "1.6+git${GITPKGV}"
VER="1.6"
PR = "r5"


SRC_URI="git://github.com/opendroid-Team/Skins-for-openOPD.git;protocol=https"

FILES_${PN} = "/"

S = "${WORKDIR}/git/OPD-Blue-Line"

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
rm -rf /usr/share/enigma2/OPD_Blue_Line
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}
pkg_preinst_${PN} () {
#!/bin/sh
echo "        OPD-Blue-Line Skin will be now installed...            "
exit 0
}
pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "              OPD-Blue-Line is now being removed...        "
echo "                                                           "
exit 0
}
do_package_qa[noexec] = "1"
