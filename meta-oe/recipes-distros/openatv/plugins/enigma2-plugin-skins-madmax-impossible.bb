SUMMARY = "MadMax Fhd Poster by Madhouse & StixMax"
MAINTAINER = "madhouse"

require conf/license/license-gplv2.inc

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "${GITPKGVTAG}"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent, enigma2-plugin-extensions-weatherplugin"

SRC_URI="git://github.com/m4dhouse/MadMax-Atv.git;protocol=https;branch=main"

FILES_${PN} = "${libdir} /usr/share"

FILES_${PN}-src = "\
    ${libdir}/enigma2/python/Components/Converter/*.py \
    ${libdir}/enigma2/python/Components/Renderer/*.py \
    ${libdir}/enigma2/python/Plugins/Extensions/MadMax/*.py \
    "

S = "${WORKDIR}/git/MadMax-Impossible-Skin"

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
echo "                                                          "
echo "                  Skin MadMax Installed                   "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/MadMax ]; then
  rm -rf /usr/lib/enigma2/python/Plugins/Extensions/MadMax
fi;
if [ -d /usr/share/enigma2/MadMax-Poster ]; then
  rm -rf /usr/share/enigma2/MadMax-Poster
fi;
if [ -d /usr/share/enigma2/MadMax-Dual-Poster ]; then
  rm -rf /usr/share/enigma2/MadMax-Dual-Poster
fi;
if [ -d /usr/share/enigma2/MadMax ]; then
  rm -rf /usr/share/enigma2/MadMax
  rm -rf /usr/lib/enigma2/python/Components/Converter/MM*
  rm -rf /usr/lib/enigma2/python/Components/Renderer/MM*
fi;
echo "                                                          "
echo "                Skin successful removed.                  "
echo "                                                          "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                          "
echo "          MadMax Skin is now being installed...           "
echo "                                                          "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                          "
echo "           MadMax Skin is now being removed...            "
echo "                                                          "
exit 0
}

do_package_qa[noexec] = "1"
