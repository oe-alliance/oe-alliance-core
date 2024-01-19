SUMMARY = "HbbTV for QT browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20181019_r1"

PR = "r1"

SRC_URI = "https://source.mynonpublic.com/gigablue/hbbtv/gb-hbbtv-qt-${SRCDATE}.zip"

SRC_URI[md5sum] = "fb71d97af77211e46e5dbfcf0ae2e61d"
SRC_URI[sha256sum] = "8ce7d977174d5c317211e3a42c911c3cc06a20a3a585fb695183b074d5e90071"

RDEPENDS:${PN}  = "qtwebkit virtual-libgles2"
RDEPENDS:${PN} += "gb-v3ddriver-${MACHINE}"

S = "${WORKDIR}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/HbbTV"

FILES:${PN} = "${bindir} ${libdir}/mozilla/plugins/libhbbtvbrowserplugin.so ${PLUGINPATH}/*.py"

do_install(){
    install -d ${D}${PLUGINPATH}
    install -m 0755 ${S}/plugin/*.py ${D}${PLUGINPATH}
    install -d ${D}${bindir}
    install -m 0755 ${S}/gb_qthbbtv ${D}${bindir}
    install -m 0755 ${S}/run_hbbtv.sh ${D}${bindir}
    install -d ${D}${libdir}/mozilla/plugins
    install -m 0755 ${S}/libhbbtvbrowserplugin.so ${D}${libdir}/mozilla/plugins
}


pkg_postinst_ontarget:${PN}(){
#!/bin/sh
ln -sf /usr/share/fonts /usr/lib/fonts

# remove old PLUGINPATH
# vbcfg.py requires PLUGINROOT = "/usr/lib/enigma2/python/Plugins/Extensions/HbbTV"
if [ -d ${libdir}/enigma2/python/Plugins/Extensions/Hbbtv ]; then
  rm -rf ${libdir}/enigma2/python/Plugins/Extensions/Hbbtv
fi

exit 0
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INSANE_SKIP:${PN} += "already-stripped file-rdeps ldflags"
