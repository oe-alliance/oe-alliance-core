SUMMARY = "Skin Full HD for HDF Images by stein17"
MAINTAINER = "openhdf"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r6"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git;protocol=https"

FILES_${PN} = "/"

S = "${WORKDIR}/git/Blue-Line-OCT-4HDF"

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
if [ -f /Blue-Line-OCT-4HDF/skin.xml ]; then
  rm -rf /usr/share/enigma2/Blue-Line-OCT-4HDF/
  mv /tmp/Blue-Line-OCT-4HDF /usr/share/enigma2/
  rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueLineFHD/
  mv /tmp/BlueLineFHD /Extensions/Plugins/python/enigma2/lib/usr/
fi
echo "                                                          "
echo " ..Blue-Line-OCT-4HDF Skin  by stein17 successful installed. "
echo "                                                          "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OCT-4HDF
rm -rf /usr/lib/enigma2/python/Components/Converter/BL*
rm -rf /usr/lib/enigma2/python/Components/Renderer/BL*
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueLineFHD*
echo "                                                                 "
echo "Blue-Line-OCT-4HDF skin was successfully removed from your receiver"
echo "                                                                 "
echo "The GUI of your receiver is now rebooting....                    "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "                                                                            "
echo "                         ***** WARNING ****                                 "
echo "Blue-Line-OCT-4HDF skin requires a minimal dual core.                         "
echo "Blue-Line-OCT-4HDF skin is NOT guaranteed to work on slower or older receivers.    "
echo "                                                                            "
echo "                                                                            "
echo "                                                                            "
echo "Check if a previous version of the Blue-Line-OCT-4HDF skin is installed"
if [ -f /usr/share/enigma2/Blue-Line-OCT-4HDF/skin.xml ]; then
    cp -R /usr/share/enigma2/Blue-Line-OCT-4HDF/ /tmp
    rm -rf /usr/share/enigma2/Blue-Line-OCT-4HDF
    rm -rf /usr/lib/enigma2/python/Components/Converter/BL*
    rm -rf /usr/lib/enigma2/python/Components/Renderer/BL*
	rm -rf /usr/lib/enigma2/python/Plugins/Extensions/BlueLineFHD*
    echo "                                                   "
    echo "Previous Blue-Line-OCT-4HDF skin installation        "
    echo "    was found and removed successfully!            "
    echo "                                                   "
fi
echo "                                                       "
echo "Blue-Line-OCT-4HDF skin is now being installed...        "
echo "                                                       "
exit 0
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
