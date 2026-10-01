SUMMARY = "HbbTV for QT browser"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

DEPENDS = "freetype"

inherit gitpkgv

SRC_URI = "git://github.com/oe-alliance/e2plugins.git;protocol=https;branch=python3"
SRC_URI += "file://0001-dvbi-hbbtv-ip-context.patch;patchdir=${S}"

PV = "1.0+git"
PR = "r1"
PKGV = "1.0+git${GITPKGV}"
SRCREV = "${AUTOREV}"
# HiSilicon machines advertise the chipset (e.g. hisil-3798mv200), not just hisil.
QVERSION ?= "${@'-v2' if any(feature == 'hisil' or feature.startswith('hisil-') for feature in d.getVar('MACHINE_FEATURES').split()) else ''}"

RDEPENDS:${PN}  = "qtwebkit libxml2-qt"

S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/qthbbtv${QVERSION}"

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
