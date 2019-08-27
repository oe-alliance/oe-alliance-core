SUMMARY = "GLAMOUR AURA FullHD  Skin for ATV Images"
MAINTAINER = "MCelliotG"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "8.x+git${SRCPV}"
PKGV = "8.x+git${GITPKGV}"
VER="8.x"

RDEPENDS_${PN} = "enigma2-plugin-extensions-weather_msn"

SRC_URI="git://github.com/MCelliotG/GlamourAuraFHD-ATV-skin.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git"

do_compile_append() {
python -O -m compileall ${S}/usr
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
echo "Glamour Aura FHD skin for OpenATV is now successfully installed in your receiver"
echo "                             Enjoy!!!                                           "

exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/GlamourAuraFHD-ATV
rm -rf /usr/lib/enigma2/python/Components/Converter/Glam*
rm -rf /usr/lib/enigma2/python/Components/Renderer/Glam*
echo "                                                                     "
echo "Glamour Aura FHD Skin was successfully removed from your receiver    "
echo "                                                                     "

exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                   *******                                  "
echo "                         ******               *****                         "
echo "                    ****                            ****                    "
echo "                 ***                                     ***                "
echo "               **     GLAMOUR AURA FHD SKIN FOR OPENATV     **             "
echo "                            a skin by MCelliotG                             "
echo "                                                                            "
echo "                                                                            "
echo "                         ***** DISCLAIMER ****                              "
echo "Glamour Aura FHD skin requires a minimal dual core CPU @ 751 Mhz.           "
echo "Glamour Aura FHD is NOT guaranteed to work on slower or older receivers.    "

exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                                   "
echo "Glamour Aura FHD skin is now being removed from your receiver      "
echo "                                                                   "

exit 0
}

do_package_qa[noexec] = "1"

