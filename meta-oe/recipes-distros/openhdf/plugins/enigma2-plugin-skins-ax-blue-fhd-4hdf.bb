SUMMARY = "Skin Full HD for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
VER="3.0"

SRC_URI="git://github.com/stein17/AX-Blue-FHD-4HDF.git;protocol=git"

FILES_${PN} = "${datadir} ${libdir}"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}${datadir}
	install -d ${D}${libdir}
	cp -rp ${S}${datadir}/* ${D}${datadir}/
	cp -rp ${S}${libdir}/* ${D}${libdir}/
	chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf ${datadir}/enigma2/AX_Blue_FHD_4HDF
rm -rf ${datadir}/enigma2/Spinner/AX_Blue
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

