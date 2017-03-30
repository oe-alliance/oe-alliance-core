SUMMARY = "openNFR Mod Webinterface"
MAINTAINER = "Carlo0815"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 python-process"
RDEPENDS_${PN} = "python-process"

RREPLACES_${PN} = "settings-autorestore"

inherit autotools-brokensep gitpkgv pythonnative
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/carlo0815/enigma2-plugins.git;protocol=git"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-po \
    --with-libsdl=no \
    "

PACKAGES =+ "${PN}-src"
CONFFILES_${PN} += "${sysconfdir}/exports"
FILES_${PN} = "/usr/lib"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/WebInterface/*.py"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/WebBouquetEditor/*.py"
FILES_${PN}-dbg = "/usr/lib/enigma2/python/Plugins/Extensions/WebAdmin/.debug/"
FILES_${PN}-src = "/usr/lib/enigma2/python/Plugins/Extensions/WebAdmin/*.py"
FILES_${PN}-doc = "/usr/share/enigma2/README*"

pkg_postinst_${PN}() {
#!/bin/sh
echo "Restarting Enigma2 to load Webinterface Plugin ..."
init 4
killall -9 enigma2 > /dev/null 2>&1
init 3
exit 0
}

do_install_append() {
}
