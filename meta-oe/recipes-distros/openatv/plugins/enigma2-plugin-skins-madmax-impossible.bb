SUMMARY = "MadMax Fhd Poster by Madhouse & StixMax"
MAINTAINER = "madhouse"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

RDEPENDS:${PN} = "enigma2-plugin-extensions-oaweather"

SRC_URI="git://github.com/m4dhouse/MadMax-Atv.git;protocol=https;branch=main"

FILES:${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git/MadMax-Impossible-Skin"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo "                  Skin MadMax Installed                   "
echo "                                                          "
exit 0
}

pkg_postrm:${PN} () {
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

pkg_preinst:${PN} () {
#!/bin/sh
echo "                                                          "
echo "          MadMax Skin is now being installed...           "
echo "                                                          "
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
echo "                                                          "
echo "           MadMax Skin is now being removed...            "
echo "                                                          "
exit 0
}

do_package_qa[noexec] = "1"
