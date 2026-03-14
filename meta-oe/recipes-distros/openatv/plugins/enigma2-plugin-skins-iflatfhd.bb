SUMMARY = "Skin iFlatFHD"
MAINTAINER = "Nathanael and gordon55"

require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PV = "5.9+git"
PKGV = "5.9+git${GITPKGV}"
VER = "5.9"

RDEPENDS:${PN} = "enigma2-plugin-systemplugins-weathercomponenthandler enigma2-plugin-skincomponents-weathercomponent"

SRC_URI = "git://github.com/openatv/iflat.git;protocol=https;branch=master"

FILES:${PN} += "${libdir} /usr/share"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}/usr/share

    cp -r --no-preserve=ownership ${S}/usr/lib/* ${D}${libdir}/
    cp -r --no-preserve=ownership ${S}/usr/share/* ${D}/usr/share/

    chmod -R a+rX ${D}/usr/share/enigma2/
}

pkg_preinst:${PN}() {
#!/bin/sh
echo "        iFlatFHD Skin will be now installed...            "
exit 0
}

pkg_postinst:${PN}() {
#!/bin/sh
# Rootfs aşamasında başarısız olmaması için boş bırakılıyor.
exit 0
}

pkg_postinst_ontarget:${PN}() {
#!/bin/sh

echo "********************************************************"
echo "*                      iFlatFHD                        *"
echo "*                    by Nathanael                      *"
echo "*                  support by gordon55                 *"
echo "********************************************************"

iFlatDir="/usr/share/enigma2/iFlatFHD"
widgetSP="skin_0ld-widgets.xml"
MPDir="/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal"

if [ ! -d "$iFlatDir" ]; then
    exit 0
fi

if [ ! -d "$iFlatDir/mySkin_off" ]; then
    mkdir -p "$iFlatDir/mySkin_off"
fi

if [ -L "$iFlatDir/mySkin_off/$widgetSP" ]; then
    echo "...skinpart for old constant-widgets is already active"
else
    echo "...activate skinpart for old constant-widgets"
    if [ -e "$iFlatDir/allScreens/$widgetSP" ]; then
        ln -sf "$iFlatDir/allScreens/$widgetSP" "$iFlatDir/mySkin_off/$widgetSP"
    fi
fi

echo "... checking activated skinparts"

count=0
for file in "$iFlatDir"/mySkin_off/*.xml; do
    if [ ! -e "$file" ]; then
        echo "    $(basename "$file") : link broken, deleting"
        rm -f "$file"
        count=$((count + 1))
    fi
done

[ "$count" = "0" ] && echo "    OK."

echo

if [ -e "$MPDir/plugin.pyo" ] || [ -e "$MPDir/plugin.py" ]; then
    echo "... install iFlatFHD for Mediaportal"
    [ -d "$MPDir/skins_1080/iFlatFHD" ] && rm -rf "$MPDir/skins_1080/iFlatFHD"
    mkdir -p "$MPDir/skins_1080"
    cp -raf "$iFlatDir/MediaPortal" "$MPDir/skins_1080/iFlatFHD"
    [ -e "$MPDir/skins_1080/iFlatFHD/skin-MP.xml" ] && mv "$MPDir/skins_1080/iFlatFHD/skin-MP.xml" "$MPDir/skins_1080/iFlatFHD/skin.xml"
else
    echo "... Mediaportal is not installed, iFlatFHD for MP will not be installed"
fi

echo "              ...Skin successful installed.                "
exit 0
}

pkg_prerm:${PN}() {
#!/bin/sh
echo "                                                           "
echo "              iFlatFHD is now being removed...             "
echo "                                                           "
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh

echo "********************************************************"
echo "*                      iFlatFHD                        *"
echo "*                    by Nathanael                      *"
echo "*                 support by gordon55                  *"
echo "********************************************************"
echo ""

MPDir="/usr/lib/enigma2/python/Plugins/Extensions/MediaPortal"

rm -rf /usr/share/enigma2/iFlatFHD

if [ -d "$MPDir/skins_1080/iFlatFHD" ]; then
    echo ".. remove iFlatFHD skin for Mediaportal"
    rm -rf "$MPDir/skins_1080/iFlatFHD"
else
    echo ".. no iFlatFHD skin for Mediaportal found, nothing to do"
fi

echo "                                                           "
echo "               ...Skin successful removed.                 "
exit 0
}

do_package_qa[noexec] = "1"
