SUMMARY = "BlackSpirit.HD Skin for Enigma2 by DeadEyE"
MAINTAINER = "DeadEyE"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.60rc2+git${SRCPV}"
PKGV = "0.60rc2+git${GITPKGV}"
VER="0.60rc2"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-extnumberzap"

SRC_URI="git://github.com/DeadEyE-OpenATV/BlackSpirit.HD.git;protocol=https"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}/usr/share/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}/usr/share/enigma2/
    ln -s ${D}/usr/share/enigma2/BlackSpirit.HD/img ${D}/usr/share/enigma2/BlackSpirit.HD/icons
    ln -s ${D}/usr/share/enigma2/BlackSpirit.HD/img ${D}/usr/share/enigma2/BlackSpirit.HD/buttons
}

pkg_postinst_${PN}() {
#!/bin/sh
if [ -f /tmp/skin.xml ]; then
    mv /tmp/skin.xml /usr/share/enigma2/BlackSpirit.HD
fi
echo "                                                          "
echo "             ...Skin successful installed.                "
echo "                                                          "
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf /usr/share/enigma2/BlackSpirit.HD
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlackSpiritHD
rm -rf /usr/lib/enigma2/python/Components/Converter/BS_*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BS_*
echo "                                                          "
echo "              ...Skin successful removed.                 "
echo "                                                          "
exit 0
}

pkg_preinst_${PN}() {
#!/bin/sh
echo "Checking for previous installations..."
if [ -f /usr/share/enigma2/BlackSpirit.HD/skin.xml ]; then
    rm -rf /usr/share/enigma2/BlackSpirit.HD
    rm -rf /usr/lib/enigma2/python/Components/Converter/BS_*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/BS_*
    echo "                                                           "
    echo "          Previous BlackSpirit.HD installation             "
    echo "                 was found and removed!                    "
    echo "                                                           "
fi
if [ -f /usr/lib/enigma2/python/Plugins/Extensions/BlackSpiritHD/plugin.py ]; then
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlackSpiritHD
    echo "                                                           "
    echo "           BlackSpirit.HD configuration plugin             "
    echo "                 was found and removed!                    "
    echo "                                                           "
fi
echo "                                                           "
echo "               Proceeding to installation...               "
echo "                                                           "
exit 0
}

pkg_prerm_${PN}() {
#!/bin/sh
echo "                                                           "
echo "             BlackSpirit.HD is now being removed...        "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"
