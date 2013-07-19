DESCRIPTION = "IPTV Bouquet Updater "
MAINTAINER = "Nobody28 & satinfo"
SECTION = "extra"
PRIORITY = "optional"
DEPENDS = "gst-plugins-bad-rtmp librtmp0 librtmp-bin"

require conf/license/license-gplv2.inc

inherit gitpkgv
SRCREV = "${AUTOREV}"
PV = "1.0.+git${SRCPV}"
PKGV = "1.0.+git${GITPKGV}"
PR = "r0"


SRC_URI="git://github.com/Nobody28/IPTV-List-Updater.git"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/usr/lib"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater/locale/*.po"

inherit autotools

EXTRA_OECONF = "\
	--with-libsdl=no --with-boxtype=${MACHINE} --with-po \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
"

do_install() {
	chmod -R 755  ${D}/usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater
}

pkg_postinst() {
#!/bin/sh
# Ein Shell Script welches nach Installation des Paketes ausgeführt wird 
echo "********************************************************"
echo "*  IPTV List Updater installed                         *"
echo "*                                                      *"
echo "*  Restart the Engima2 GUI to activate the plugin      *"
echo "********************************************************"
exit 0
}

pkg_postrm() {
#!/bin/sh
# Ein Shell Script welches nach Entfernen des Paketes ausgeführt wird 
echo "Removing IPTV List Updater Plugin from the system ..."
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater > /dev/null 2>&1
rm -rf /usr/script/IPTV-List-update.sh > /dev/null 2>&1
exit 0
}

pkg_preinst() {
#!/bin/sh
# Ein Shell Script welches vor Installation des Paketes ausgeführt wird 
echo "Checking for an older version of IPTV List Updater in the system..."
if [ -d /usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater ]
	then
		rm -rf /usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater > /dev/null 2>&1
		rm -rf /usr/script/IPTV-List-update.sh > /dev/null 2>&1
		echo "An older version of IPTV List Updater was found and removed"
		echo "Proceeding to installation..."
	else
		echo "IPTV List Updater was not found in the system"
		echo "Proceeding to installation..."
fi
exit 0
}

pkg_prerm() {
#!/bin/sh
# Ein Shell Script welches vor Entfernen des Paketes ausgeführt wird 
rm /usr/lib/enigma2/python/Plugins/Extensions/IPTV-List-Updater/*.pyo > /dev/null 2>&1
exit 0
}