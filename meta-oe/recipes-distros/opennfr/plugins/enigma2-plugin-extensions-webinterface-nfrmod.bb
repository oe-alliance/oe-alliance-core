SUMMARY = "openNFR Mod Webinterface"
MAINTAINER = "Carlo0815"

require conf/license/license-gplv2.inc

DEPENDS = "enigma2 ${PYTHON_PN}-process"
RDEPENDS:${PN} = "${PYTHON_PN}-process"

RREPLACES:${PN} = "settings-autorestore"

inherit autotools-brokensep gitpkgv ${PYTHON_PN}native
SRCREV = "${AUTOREV}"
PV = "3.0+git${SRCPV}"
PKGV = "3.0+git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/carlo0815/enigma2-plugins.git;protocol=https"

S = "${WORKDIR}/git"

EXTRA_OECONF = "\
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-po \
    --with-libsdl=no \
    "

CONFFILES:${PN} += "${sysconfdir}/exports"
FILES:${PN} = "${libdir}"
FILES:${PN}-dbg = "${libdir}/enigma2/python/Plugins/Extensions/WebInterface/.debug/"
FILES:${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/WebInterface/*.py"
FILES:${PN}-dbg = "${libdir}/enigma2/python/Plugins/Extensions/WebBouquetEditor/.debug/"
FILES:${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/WebBouquetEditor/*.py"
FILES:${PN}-dbg = "${libdir}/enigma2/python/Plugins/Extensions/WebAdmin/.debug/"
FILES:${PN}-src = "${libdir}/enigma2/python/Plugins/Extensions/WebAdmin/*.py"
FILES:${PN}-doc = "/usr/share/enigma2/README*"

pkg_postinst:${PN}() {
#!/bin/sh
echo "Restarting Enigma2 to load Webinterface Plugin ..."
init 4
killall -9 enigma2 > /dev/null 2>&1
init 3
exit 0
}

do_install:append() {
}
