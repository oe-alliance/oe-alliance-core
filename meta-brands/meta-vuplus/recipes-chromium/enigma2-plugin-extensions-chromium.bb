DESCRIPTION = "E2 Chromium Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "20160331_r1"
SRC_URI = "http://code.vuplus.com/download/chromium/e2plugin-chromium-vuplus_${PR}.tar.gz"

DEPENDS_${PN} = "chromium-browser"
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

SRC_URI[md5sum] = "48ee52ec132fc7447f555aafbf8c7fcd"
SRC_URI[sha256sum] = "f271338155df55eed5a1c56f18c9c7ced09d025d1e3c36c9e2e4c25f959bbea6"

