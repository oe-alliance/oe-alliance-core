SUMMARY = "Filemanager MoviePlayer Extentions"
MAINTAINER = "Coolman, Betonme & Swiss-MAD"
SECTION = "extra"
PRIORITY = "optional"
RDEPENDS_${PN} = "python-json python-html gst-plugins-good-flv gst-plugins-bad-rtmp librtmp1"

require conf/license/license-gplv2.inc

inherit gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "4.0.+git${SRCPV}"
PKGV = "4.0.+git${GITPKGV}"
PR = "r7"


SRC_URI="git://github.com/betonme/e2openplugin-EnhancedMovieCenter.git"

S = "${WORKDIR}/git"

PACKAGES =+ "${PN}-src"
PACKAGES =+ "${PN}-po"
FILES_${PN} = "/etc /usr/lib"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/*.py"
FILES_${PN}-po = "/usr/lib/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/locale/*/*/*.po"

inherit autotools-brokensep

EXTRA_OECONF = "\
    --with-libsdl=no --with-boxtype=${MACHINE} --with-po \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

CONFFILES_${PN} = "${sysconfdir}/enigma2/emc-hide.cfg ${sysconfdir}/enigma2/emc-noscan.cfg ${sysconfdir}/enigma2/emc-permsort.cfg ${sysconfdir}/enigma2/emc-topdir.cfg"


pkg_postinst_${PN}() {
#!/bin/sh
echo ""
echo ""
echo "***********************************"
echo "*     Enhanced Movie Center       *"
echo "*             V 4.0               *"
echo "*               by                *"
echo "*   Coolman, Betonme & Swiss-MAD  *"
echo "***********************************"
echo ""
echo ""
echo "Plugin successfully installed!"
echo ""
echo "You should restart enigma2 now..."
echo ""
echo ""
echo ""
exit 0
}

pkg_postrm_${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}