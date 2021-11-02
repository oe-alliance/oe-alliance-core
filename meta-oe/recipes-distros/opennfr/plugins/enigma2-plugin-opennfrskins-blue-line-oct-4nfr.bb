SUMMARY = "Skin Full HD for NFR Images by stein17"
MAINTAINER = "opennfr"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "2.1+git${SRCPV}"
PKGV = "2.1+git${GITPKGV}"
VER ="2.1"
PR = "r3"

SRC_URI="git://github.com/stein17/Skins-for-openNFR.git;protocol=https"

S = "${WORKDIR}/git/Blue-Line-OCT-4NFR"

FILES_${PN} = "/tmp ${libdir} /usr/share"

do_install() {
	install -d ${D}/tmp/nfr/
	install -d ${D}/tmp/octagon/
	cp -rp ${S}/tmp/nfr/* ${D}/tmp/nfr/
	cp -rp ${S}/tmp/octagon/* ${D}/tmp/octagon/
	install -d ${D}${libdir}
    	install -d ${D}/usr/share
    	cp -rp ${S}/usr/lib/* ${D}${libdir}/
    	cp -rp ${S}/usr/share/* ${D}/usr/share/
}

pkg_postinst_${PN} () {
#!/bin/sh
if grep -qs 'getMachineBrand=Octagon' cat /tmp/boxbranding.cfg  ; then
	echo "                                 "
	echo "*********************************"
	echo "Octagon Box found!"
	echo "*********************************"
	echo "                                 "
	cp /tmp/octagon/*.png /usr/share/enigma2/Blue-Line-OCT-4NFR/menu	
else
	echo "                                 "
	echo "*********************************"
	echo "No Octagon Box found!"
	echo "*********************************"
	echo "                                 "
       	cp /tmp/nfr/*.png /usr/share/enigma2/Blue-Line-OCT-4NFR/menu
fi

echo " Skin Blue-Line-OCT-4NFR installed "

exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/Blue-Line-OCT-4NFR/
echo ""
echo ""
echo "Skin removed! You should restart GUI now!"
echo ""
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh

python ${libdir}/enigma2/python/BoxBrandingTest.pyo | sed 's/<$//' | sed 's/ /_/g' > /tmp/boxbranding.cfg

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
