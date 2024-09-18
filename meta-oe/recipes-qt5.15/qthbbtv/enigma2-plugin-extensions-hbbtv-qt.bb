SUMMARY = "HbbTV for QT browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "freetype"

inherit gitpkgv

SRC_URI = "git://github.com/oe-alliance/e2plugins.git;protocol=https;branch=python3"

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
SRCREV = "${AUTOREV}"
QVERSION ?= "${@bb.utils.contains('MACHINE_FEATURES', 'hisil', '-v2', '', d)}"

RDEPENDS:${PN}  = "qtwebkit"

S = "${WORKDIR}/git/qthbbtv${QVERSION}"

FILES:${PN} =  "${bindir} ${libdir}"

do_install(){
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/plugin/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -d ${D}${bindir}
    install -m 0755 ${S}/qthbbtv ${D}${bindir}
    install -d ${D}${libdir}/mozilla/plugins
    install -m 0755 ${S}/libnpapihbbtvplugin.so ${D}${libdir}/mozilla/plugins
}

pkg_postinst_ontarget:${PN}(){
#!/bin/sh
ln -sf /usr/share/fonts /usr/lib/fonts
exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} += "already-stripped file-rdeps ldflags"
