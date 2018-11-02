SUMMARY = "HbbTV for QT browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20181019_r0"

inherit gitpkgv

SRC_URI = "http://source.mynonpublic.com/gigablue/hbbtv/gb-hbbtv-qt-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "4608df53d3fa0aecf190a8db6e26e0df"
SRC_URI[sha256sum] = "4ef50f0d78c2bb3778c28d8432705310b77c96183c549fa436d42196acc29824"

RDEPENDS_${PN}  = "qtwebkit virtual/libgles2"
RDEPENDS_${PN} += "gb-v3ddriver-${MACHINE}"

S = "${WORKDIR}"

FILES_${PN} =  "${bindir} ${libdir}"

do_install(){
    install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/Hbbtv
    install -m 0755 ${S}/plugin/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/Hbbtv
    install -d ${D}${bindir}
    install -m 0755 ${S}/gb_qthbbtv ${D}${bindir}
    install -m 0755 ${S}/run_hbbtv.sh ${D}${bindir}
    install -d ${D}${libdir}/mozilla/plugins
    install -m 0755 ${S}/libhbbtvbrowserplugin.so ${D}${libdir}/mozilla/plugins
}

pkg_postinst_${PN}(){
#!/bin/sh
ln -sf /usr/share/fonts /usr/lib/fonts
exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP_${PN} += "already-stripped"

