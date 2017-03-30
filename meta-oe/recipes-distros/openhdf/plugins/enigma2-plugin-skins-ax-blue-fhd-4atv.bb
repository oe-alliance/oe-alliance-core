SUMMARY = "Skin Full HD for ATV Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "2.5+git${SRCPV}"
PKGV = "2.5+git${GITPKGV}"
VER="2.5"

SRC_URI="git://github.com/stein17/AX-Blue-FHD-4ATV.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/AX_Blue_FHD_4ATV
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

