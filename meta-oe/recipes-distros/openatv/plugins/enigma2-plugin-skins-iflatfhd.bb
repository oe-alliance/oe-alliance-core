SUMMARY = "Skin iFlatFHD"
MAINTAINER = "Nathanael and gordon55"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "5.9+git${SRCPV}"
PKGV = "5.9+git${GITPKGV}"
VER="5.9"

RDEPENDS_${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler, enigma2-plugin-skincomponents-weathercomponent"

SRC_URI="git://github.com/openatv/iflat.git;protocol=git"

FILES_${PN} = "${libdir} /usr/share"

S = "${WORKDIR}/git"

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

echo "********************************************************"
echo "*                      iFlatFHD                        *"
echo "*                    by Nathanael                      *"
echo "*                  support by gordon55                 *"
echo "********************************************************"

iFlatDir="/usr/share/enigma2/iFlatFHD"
widgetSP="skin_0ld-widgets.xml"						# file name of the skinpart

if [ -L "$iFlatDir/mySkin_off/$widgetSP" ]
  then
		echo -e "...skinpart for old constant-widgets is already active"
  else
		echo -e "..activate skinpart for old constant-widgets"
		ln -sf "$iFlatDir/allScreens/$widgetSP" "$iFlatDir/mySkin_off/$widgetSP"
fi

echo "... checking activated skinparts"

count=0
for file in $iFlatDir/mySkin_off/*.xml
do
	#echo "--${file}--"
	if [ ! -e "$file" ]; then
		echo "    `basename "$file"` :  link broken, deleting"
		rm -f "$file"
		count=`expr $count + 1`
	fi
done
[ $count == 0 ] && echo "    OK."

echo

if [ -e "${libdir}/enigma2/python/Plugins/Extensions/MediaPortal/plugin.pyo" ]
  then
		echo -e "... install iFlatFHD for Mediaportal"
		[ -d "$MPDir/skins_1080/iFlatFHD" ] && rm -rf "$MPDir/skins_1080/iFlatFHD"
		cp -raf "$iFlatDir/MediaPortal" "$MPDir/skins_1080/iFlatFHD"
		mv "$MPDir/skins_1080/iFlatFHD/skin-MP.xml" "$MPDir/skins_1080/iFlatFHD/skin.xml"
  else
		echo -e "...   Mediaportal is not installed,\n     iFlatFHD for MP will not be installed"
fi
echo "              ...Skin successful installed.                "
exit 0
}

pkg_postrm_${PN} () {
#!/bin/sh
rm -rf /usr/share/enigma2/iFlatFHD

echo "********************************************************"
echo "*                      iFlatFHD                        *"
echo "*                    by Nathanael                      *"
echo "*                 support by gordon55                  *"
echo "********************************************************"
echo ""

# --- check if Mediaportal is installed. if yes, remove iFlatFHD skin for Mediaportal

MPDir="${libdir}/enigma2/python/Plugins/Extensions/MediaPortal"

if [ -d "$MPDir/skins_1080/iFlatFHD" ]
  then
		echo -e ".. remove iFlatFHD skin for Mediaportal"
		rm -rf "$MPDir/skins_1080/iFlatFHD"
	else
		echo -e "..   no iFlatFHD skin for Mediaportal found, nothing to do"
fi
echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

pkg_preinst_${PN} () {
#!/bin/sh
echo "        iFlatFHD Skin will be now installed...            "
exit 0
}

pkg_prerm_${PN} () {
#!/bin/sh
echo "                                                           "
echo "              iFlatFHD is now being removed...             "
echo "                                                           "
exit 0
}

do_package_qa[noexec] = "1"

