DESCRIPTION = "E2 Chromium Plugin"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "20180628_r0"
SRC_URI = "http://source.mynonpublic.com/gigablue/chromium/e2plugin-chromium-gigablue_${PR}.tar.gz"

COMPATIBLE_MACHINE = "^(gb7252)$"

DEPENDS = "chromium-browser"
RDEPENDS_${PN} = "chromium-browser"

do_install_append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/Chromium
    cp -aRf ${WORKDIR}/e2plugin/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/Chromium/
}

do_package_qa() {
}

PROVIDES += "enigma2-plugin-extensions-chromium"
RPROVIDES_${PN} += "enigma2-plugin-extensions-chromium"

FILES_${PN} = "/"

SRC_URI[md5sum] = "6b1db0620fd187252d1195a4389da930"
SRC_URI[sha256sum] = "9723574753390358d567bcfeb364485f26d279417436c927f0534d27c24f9779"

