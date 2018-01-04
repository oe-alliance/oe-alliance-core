SUMMARY = "QT browser for HbbTV"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20170928"

SRC_URI = "file://qthbbtv-106.zip"

PV = "1.2"

RDEPENDS_${PN}  = "qtwebkit virtual/libgles2"

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
}

pkg_postinst_${PN}() {
#!/bin/sh
ln -sf /usr/share/fonts /usr/lib/fonts
exit 0
}


INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "already-stripped"

