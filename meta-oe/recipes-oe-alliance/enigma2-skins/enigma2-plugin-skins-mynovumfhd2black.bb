SUMMARY = "myNOVUM_FullHD2_Black by Nashu"

require conf/license/license-gplv2.inc

inherit gitpkgv allarch

SRCREV = "${AUTOREV}"
PV = "8.7+git${SRCPV}"
PKGV = "8.7+git${GITPKGV}"
VER="8.7"

SRC_URI = "git://github.com/oe-alliance/oe-alliance-skins/Nashu/myNOVUM_FHD2_Black.git;protocol=git"

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

pkg_postinst_${PN}() {
#!/bin/sh
echo "                                                          "
echo "             ...Skin successful installed.                "
echo "                                                          "
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf /usr/share/enigma2/myNOVUM_FHD2_Black
rm -rf /usr/lib/enigma2/python/Components/Renderer/NovSingleEpgListNobile.pyo
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
exit 0
}

pkg_preinst_${PN}() {
#!/bin/sh
echo "                                                           "
echo "               Proceeding to installation...               "
echo "                                                           "
exit 0
}

pkg_prerm_${PN}() {
#!/bin/sh
echo "                                                           "
echo "             ...Skin is now being removed...               "
echo "                                                           "
exit 0
}

