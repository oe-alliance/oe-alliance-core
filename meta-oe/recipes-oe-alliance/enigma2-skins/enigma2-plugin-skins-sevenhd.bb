SUMMARY = "Skin for Enigma2 (HD, FHD, UHD, 4K)"
MAINTAINER = "Team Kraven"
inherit allarch

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "3.6.83+git${SRCPV}"
PKGV = "3.6.83+git${GITPKGV}"
VER="3.6.83"

RDEPENDS_${PN} = "python-requests python-subprocess python-imaging enigma2-plugin-systemplugins-mphelp python-lxml"

SRC_URI="git://github.com/openatv/SevenHD.git;protocol=git"

FILES_${PN} = "/usr/*"

S = "${WORKDIR}/git/data"

do_compile_append() {
python -O -m compileall ${S}
}

do_install() {
    install -d ${D}${datadir}/enigma2
    cp -rp ${S}/usr ${D}/
    chmod -R a+rX ${D}${datadir}/enigma2/
}

pkg_postinst_${PN} () {
#!/bin/sh
if [ -d /tmp/user ]; then
    cp -r /tmp/user/* ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/user
fi

if [ -f /tmp/skin-user.xml ]; then
    cp /tmp/skin-user.xml ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/data
fi

echo "                                                          "
echo "             ...Skin successful installed.                "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf ${datadir}/enigma2/SevenHD
rm -rf ${libdir}/enigma2/python/Plugins/Extensions/SevenHD
rm -rf ${libdir}/enigma2/python/Components/Converter/SevenHD*
rm -rf ${libdir}/enigma2/python/Components/Renderer/SevenHD*
echo " "
echo " ...Skin successful removed. "
echo " "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "Checking for previous installations..."
if [ -d ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/user ]; then
    mkdir -p /tmp/user 
    cp -r ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/user/* /tmp/user
fi

if [ -f ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/data/skin-user.xml ]; then
    cp ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/data/skin-user.xml /tmp
fi

if [ -f ${libdir}/enigma2/python/Plugins/Extensions/SevenHD/plugin.py ]; then
    rm -rf ${libdir}/enigma2/python/Plugins/Extensions/SevenHD
    rm -rf ${libdir}/enigma2/python/Components/Converter/SevenHD*
    rm -rf ${libdir}/enigma2/python/Components/Renderer/SevenHD*
    echo "                                                           "
    echo "           SevenHD configuration plugin                    "
    echo "              was found and removed!                       "
    echo "                                                           "
fi

echo "                                                           "
echo "         The Skin SevenHD is now being installed...        "
echo "                                                           "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo " "
echo " The Skin SevenHD is now being removed... "
echo " "
exit 0
}

do_package_qa[noexec] = "1"

