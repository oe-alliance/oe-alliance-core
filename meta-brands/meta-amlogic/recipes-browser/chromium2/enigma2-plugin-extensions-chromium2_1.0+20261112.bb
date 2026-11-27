SUMMARY = "Chromium2 framebuffer browser and YouTube TV for the GTX Combo"
require conf/license/license-close.inc
COMPATIBLE_MACHINE = "^gtxcombo$"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/python/python3-compileall.inc

SRC_URI = "${AMLOGIC_SOURCE_MIRROR}/chromium2-fb-dream.tar.gz"
SRC_URI[sha256sum] = "d1dd15e4ac58835232efdcf97efe794ef5f6a6dca69f36898b233aa696493b21"
S = "${UNPACKDIR}"
RDEPENDS:${PN} = "gtx-mali-fbdev nss nspr alsa-lib expat fontconfig python3-core python3-netserver curl"
RPROVIDES:${PN} += "enigma2-plugin-extensions-chromium"
RCONFLICTS:${PN} += "enigma2-plugin-extensions-chromium enigma2-plugin-extensions-netflix"
RREPLACES:${PN} += "enigma2-plugin-extensions-chromium"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
# Chromium locates its private SwiftShader libraries relative to the binary.
INSANE_SKIP:${PN} += "already-stripped dev-so libdir"
FILES:${PN} += "/usr/share/netflix ${libdir}/enigma2/python/Plugins/Extensions/Chromium"

do_install() {
    install -d ${D}${bindir} ${D}/usr/share/netflix ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium
    cp -a --no-preserve=ownership ${S}/runtime/. ${D}/usr/share/netflix/
    cp -a --no-preserve=ownership ${S}/plugin/. ${D}${libdir}/enigma2/python/Plugins/Extensions/Chromium/
    ln -s /usr/share/netflix/bin ${D}${bindir}/netflix
}
