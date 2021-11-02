SUMMARY = "Steampunk Skin for HDF Images"
MAINTAINER = "stein17"

require conf/license/license-gplv2.inc

inherit gitpkgv 

SRCREV = "${AUTOREV}"
PV = "1.1+git${SRCPV}"
PKGV = "1.1+git${GITPKGV}"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https"

S = "${WORKDIR}/git/Steampunk-HD-Skin-4HDF"

do_compile() {
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
if [ -f /Steampunk/skin.xml ]; then
    rm -rf /usr/share/enigma2/Steampunk/
    mv /tmp/Steampunk /usr/share/enigma2/
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SteampunkHD/
    mv /tmp/SteampunkHD /Extensions/Plugins/python/enigma2/lib/usr/
fi
echo "                                                          "
echo " ..Steampunk Skin  by stein17 successful installed..      "
echo "                                                          "
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Steampunk
rm -rf /usr/lib/enigma2/python/Components/Converter/Steampunk*
rm -rf /usr/lib/enigma2/python/Components/Renderer/Steampunk*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SteampunkHD*
echo "                                                          "
echo "Steampunk skin was successfully removed from your receiver"
echo "                                                          "
echo "The GUI of your receiver is now rebooting....             "
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                                            "
echo "                         ***** WARNING *****                                "
echo "Steampunk skin requires a minimal dual core.                                "
echo "Steampunk skin is NOT guaranteed to work on slower or older receivers.      "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "Check if a previous version of the Steampunk skin is installed              "
if [ -f /usr/share/enigma2/Steampunk/skin.xml ]; then
    cp -R /usr/share/enigma2/Steampunk/ /tmp
    rm -rf /usr/share/enigma2/Steampunk
    rm -rf /usr/lib/enigma2/python/Components/Converter/Steampunk*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/Steampunk*
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/SteampunkHD*
    echo "                                                          "
    echo "    Previous Steampunk skin installation                  "
    echo "     was found and removed successfully!                  "
    echo "                                                          "
fi
echo "                                                       "
echo "Steampunk skin is now being installed...               "
echo "                                                       "
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                                           "
echo " Steampunk Skin by stein17 is now being removed from your receiver...      "
echo "                                                                           "
}

FILES_${PN} = "/"

do_package_qa[noexec] = "1"
