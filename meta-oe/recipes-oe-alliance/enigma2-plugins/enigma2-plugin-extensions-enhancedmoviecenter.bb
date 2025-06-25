SUMMARY = "Filemanager MoviePlayer Extentions"
MAINTAINER = "Coolman, Betonme & Swiss-MAD"
SECTION = "extra"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

DEPENDS = "python3-six-native"
RDEPENDS:${PN} = "gstreamer1.0-plugins-good-flv gstreamer1.0-plugins-bad-rtmp python3-json python3-html python3-requests python3-mutagen rtmpdump python3-chardet python3-tmdbsimple"

inherit gitpkgv python3native autotools-brokensep gettext

SRCREV = "${AUTOREV}"
PV = "4.0.+git"
PKGV = "4.0.+git${GITPKGV}"

SRC_URI = "git://github.com/oe-mirrors/e2openplugin-EnhancedMovieCenter.git;branch=master;protocol=https"
SRC_URI:openatv ?= "git://github.com/oe-mirrors/EnhancedMovieCenter.git;protocol=https;branch=master"
SRC_URI:opendroid ?= "git://github.com/oe-mirrors/EnhancedMovieCenter.git;protocol=https;branch=master"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

PARALLEL_MAKEINST = ""

do_configure:prepend:openatv () {
    sed 's/config.EMC.use_orig_skin = ConfigYesNo(default=True)/config.EMC.use_orig_skin = ConfigYesNo(default=False)/g' -i ${S}/src/plugin.py
    sed 's/config.EMC.movie_date_format = ConfigSelection(default="%d.%m.%Y %H:%M", choices=date_choices)/config.EMC.movie_date_format = ConfigSelection(default="%d.%m.%Y", choices=date_choices)/g' -i ${S}/src/plugin.py
}

do_configure:prepend:beyonwiz () {
    sed 's/config.EMC.use_orig_skin = ConfigYesNo(default=True)/config.EMC.use_orig_skin = ConfigYesNo(default=False)/g' -i ${S}/src/plugin.py
}

CONFFILES:${PN} = "${sysconfdir}/enigma2/emc-hide.cfg ${sysconfdir}/enigma2/emc-noscan.cfg ${sysconfdir}/enigma2/emc-permsort.cfg ${sysconfdir}/enigma2/emc-topdir.cfg"

PACKAGES =+ "${PN}-po"
FILES:${PN} = "${sysconfdir} ${libdir}"

FILES:${PN}-po = "${libdir}/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/locale/*/*/*.po"

do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"

pkg_postinst:${PN}() {
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

pkg_postrm:${PN}() {
#!/bin/sh
rm -rf /usr/lib/enigma2/python/Plugins/Extensions/EnhancedMovieCenter/
echo "Plugin removed! You should restart enigma2 now!"
exit 0
}
