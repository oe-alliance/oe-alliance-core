SUMMARY = "QT browser for HbbTV"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20170928"

SRC_URI = "file://qthbbtv-106.zip"

PV = "1.0"
PR = "${SRCDATE}"

RDEPENDS_${PN}  = "qtwebkit"

S = "${WORKDIR}/files"

FILES_${PN} =  "${bindir} ${libdir}"

do_install() {
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/__init__.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/browser.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/datasocket.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/hbbtv.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -m 0755 ${S}/plugin.py ${D}${libdir}/enigma2/python/Plugins/Extensions/QtHbbtv
    install -d ${D}${bindir}
    install -m 0755 ${S}/qthbbtv ${D}${bindir}
    install -d ${D}${libdir}/mozilla/plugins
    install -m 0755 ${S}/libnpapihbbtvplugin.so ${D}${libdir}/mozilla/plugins
    ln -s /usr/share/fonts ${D}${libdir}/fonts
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "already-stripped"
