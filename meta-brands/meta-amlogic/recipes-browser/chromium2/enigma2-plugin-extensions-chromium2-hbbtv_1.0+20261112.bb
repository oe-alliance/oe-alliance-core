SUMMARY = "Chromium2 HbbTV extension for the GTX Combo"
require conf/license/license-close.inc
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/python/python3-compileall.inc

SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/chromium2-hbbtv-fb-dream.tar.gz"
SRC_URI[sha256sum] = "364979117fbcf615111469c003963d0935178683c915b5b650cce4357a22e0f2"
S = "${UNPACKDIR}"
RDEPENDS:${PN} = "enigma2-plugin-extensions-chromium2 (>= 1.0+20261112) python3-core python3-twisted-core"
RCONFLICTS:${PN} = "enigma2-plugin-extensions-openhbbtvbrowser"
FILES:${PN} += "/usr/share/chromium2-hbbtv ${libdir}/enigma2/python/Plugins/Extensions/Chromium2HbbTV"

do_install() {
    install -d ${D}/usr/share/chromium2-hbbtv ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium2HbbTV ${D}${bindir}
    install -m 0755 ${S}/bin ${D}/usr/share/chromium2-hbbtv/
    install -m 0644 ${S}/runtime/*.js ${S}/runtime/LICENSE ${D}/usr/share/chromium2-hbbtv/
    install -m 0644 ${S}/plugin/*.py ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium2HbbTV/
    install -m 0755 ${S}/collect-debug ${D}${bindir}/chromium2-hbbtv-log
}
