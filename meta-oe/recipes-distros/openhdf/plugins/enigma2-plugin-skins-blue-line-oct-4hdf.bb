SUMMARY = "Skin Full HD for HDF Images by stein17"
MAINTAINER = "openhdf"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r4"

SRC_URI="git://github.com/stein17/Skins-for-openHDF.git"

FILES_${PN} = "/"

S = "${WORKDIR}/git/Blue-Line-OCT-4HDF"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share
    cp -rp ${S}/usr/lib/* ${D}${libdir}/
    cp -rp ${S}/usr/share/* ${D}/usr/share/
    chmod -R a+rX ${D}/usr/share/enigma2/
    cp -rp ${S}/tmp ${D}/
}

pkg_postinst_${PN} () {
#!/bin/sh
if grep -qs 'getMachineBrand=Octagon' cat /tmp/boxbranding.cfg  ; then
	echo "                                 "
	echo "*********************************"
	echo "Octagon Box found!"
	echo "*********************************"
	echo "                                 "
	cp /tmp/octagon/*.png /usr/share/enigma2/Blue-Line-OCT-4HDF/menu	
else
	echo "                                 "
	echo "*********************************"
	echo "No Octagon Box found!"
	echo "*********************************"
	echo "                                 "
	cp /tmp/hdf/*.png /usr/share/enigma2/Blue-Line-OCT-4HDF/menu
fi

echo " Skin Blue-Line-OCT-4HDF installed "

exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OCT-4HDF/
echo ""
echo ""
echo "Skin removed! You should restart GUI now!"
echo ""
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
${PYTHON_PN} ${libdir}/enigma2/python/BoxBrandingTest.pyo | sed 's/<$//' | sed 's/ /_/g' > /tmp/boxbranding.cfg
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "              AX-Blue-FHD is now being removed...          "
echo "                                                           "
exit 0
}

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
