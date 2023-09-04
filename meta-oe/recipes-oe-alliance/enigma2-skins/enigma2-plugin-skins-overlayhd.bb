DESCRIPTION = "OverlayHD skin and management plugin for Enigma2 PVRs by IanSav"
SUMMARY = "OverlayHD skin and management plugin for Enigma2 PVRs by IanSav"
SECTION = "skins"
PRIORITY = "optional"
MAINTAINER = "IanSav"
LICENSE = "GPL-2.0-only"
HOMEPAGE = "https://github.com/IanSav"
SOURCE = "https://github.com/IanSav/OverlayHD"

DEPENDS = "gettext-native"

require conf/license/license-gplv2.inc

inherit allarch gittag

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "${GITPKGVTAG}"

SRC_URI = "git://github.com/IanSav/OverlayHD.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES:${PN} = "${libdir} ${datadir}"

do_compile() {
    # generate translation .mo files
	mkdir -p ${S}/locale
	for f in $(find ${S}/po -name *.po ); do
		l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*po\///')
		mkdir -p ${S}/locale/${l%}/LC_MESSAGES
		msgfmt -o ${S}/locale/${l%}/LC_MESSAGES/OverlayHD.mo ${S}/po/$l.po
	done
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${datadir}
    cp -rf ${S}/usr/lib/* ${D}${libdir}/
    cp -rf ${S}/usr/share/* ${D}${datadir}/
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/OverlayHD/locale/
    cp -rf ${S}/locale/* ${D}${libdir}/enigma2/python/Plugins/Extensions/OverlayHD/locale/
}

pkg_postinst:${PN} () {
#!/bin/sh
echo ""
echo "+-------------------------------------------------------------+"
echo "| OverlayHD skin and plugin successfully installed / updated. |"
echo "+-------------------------------------------------------------+"
echo "| Skin and Plugin developed by IanSav.                        |"
echo "| https://github.com/IanSav/OverlayHD/                        |"
echo "+-------------------------------------------------------------+"
echo ""
echo "Use the skin setup menu to activate the OverlayHD skin.  The"
echo "OverlayHD Skin Manager plugin will become active when the"
echo "OverlayHD skin is active."
echo ""
echo ""
BOOTIMAGE=`sed -En 's|config\.plugins\.skin\.OverlayHD\.BootImage=(.+)|\1|p' /etc/enigma2/settings`
if [ -n "${BOOTIMAGE}" ]; then
	ln -s "${BOOTIMAGE}" "/usr/share/enigma2/OverlayHD/bootlogo.mvi"
fi
BACKDROP=`sed -En 's|config\.plugins\.skin\.OverlayHD\.BackgroundImage=(.+)|\1|p' /etc/enigma2/settings`
if [ -n "${BACKDROP}" ]; then
	ln -s "${BACKDROP}" "/usr/share/enigma2/OverlayHD/backdrop.mvi"
fi
RADIOIMAGE=`sed -En 's|config\.plugins\.skin\.OverlayHD\.RadioImage=(.+)|\1|p' /etc/enigma2/settings`
if [ -n "${RADIOIMAGE}" ]; then
	ln -s "${RADIOIMAGE}" "/usr/share/enigma2/OverlayHD/radio.mvi"
fi
SPINNER=`sed -En 's|config\.plugins\.skin\.OverlayHD\.Spinner=(.+)|\1|p' /etc/enigma2/settings`
if [ -n "${SPINNER}" ]; then
	ln -s "${SPINNER}" "/usr/share/enigma2/OverlayHD/spinner"
fi
exit 0
}

pkg_prerm:${PN} () {
#!/bin/sh
SKIN=`sed -En 's|config\.skin\.primary_skin=(.+)/skin\.xml|\1|p' /etc/enigma2/settings`
if [ "${SKIN}" == "OverlayHD" ]; then
	echo ""
	echo "OverlayHD is the current skin. Please select another"
	echo "skin as the active skin ASAP."
	echo ""
	echo "Note that the system may become unstable if OverlayHD"
	echo "is not reinstalled or another skin is not selected"
	echo "promptly!"
	echo ""
	echo ""
fi
if [ -L "/usr/share/enigma2/OverlayHD/bootlogo.mvi" ]; then
	unlink "/usr/share/enigma2/OverlayHD/bootlogo.mvi"
fi
if [ -L "/usr/share/enigma2/OverlayHD/backdrop.mvi" ]; then
	unlink "/usr/share/enigma2/OverlayHD/backdrop.mvi"
fi
if [ -L "/usr/share/enigma2/OverlayHD/radio.mvi" ]; then
	unlink "/usr/share/enigma2/OverlayHD/radio.mvi"
fi
if [ -L "/usr/share/enigma2/OverlayHD/spinner" ]; then
	unlink "/usr/share/enigma2/OverlayHD/spinner"
fi
exit 0
}
