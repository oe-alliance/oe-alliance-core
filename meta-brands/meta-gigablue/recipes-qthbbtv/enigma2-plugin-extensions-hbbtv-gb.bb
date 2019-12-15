SUMMARY = "HbbTV for QT browser"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRCDATE = "20181019_r0"

PR = "r1"

inherit gitpkgv

SRC_URI = "http://source.mynonpublic.com/gigablue/hbbtv/gb-hbbtv-qt-${SRCDATE}.tar.gz"

SRC_URI[md5sum] = "4608df53d3fa0aecf190a8db6e26e0df"
SRC_URI[sha256sum] = "4ef50f0d78c2bb3778c28d8432705310b77c96183c549fa436d42196acc29824"

RDEPENDS_${PN}  = "qtwebkit virtual/libgles2"
RDEPENDS_${PN} += "gb-v3ddriver-${MACHINE}"

S = "${WORKDIR}"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/HbbTV"

PACKAGES =+ "${PN}-src"
FILES_${PN} = "${bindir} ${libdir}/mozilla/plugins/libhbbtvbrowserplugin.so ${PLUGINPATH}/*.pyo"
FILES_${PN}-src = "${PLUGINPATH}/*.py"

do_configure_prepend () {
    sed 's/reader.doDump()/#reader.doDump()/g' -i ${S}/plugin/plugin.py
}

do_install(){
    install -d ${D}${PLUGINPATH}
    install -m 0755 ${S}/plugin/*.py ${D}${PLUGINPATH}
    install -d ${D}${bindir}
    install -m 0755 ${S}/gb_qthbbtv ${D}${bindir}
    install -m 0755 ${S}/run_hbbtv.sh ${D}${bindir}
    install -d ${D}${libdir}/mozilla/plugins
    install -m 0755 ${S}/libhbbtvbrowserplugin.so ${D}${libdir}/mozilla/plugins
}

# Just a quick hack to "compile" the python parts.
do_install_append() {
    python -O -m compileall ${D}
}

pkg_postinst_ontarget_${PN}(){
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

INSANE_SKIP_${PN} += "already-stripped"

