DESCRIPTION = "E2 Chromium Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "20160118_r0"
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

SRC_URI[md5sum] = "bd5002f0182b26b5f77dc0186d9ba7ba"
SRC_URI[sha256sum] = "d4b46befa72b785187af161e9ea1000001cd6a170c5ac56410abf60ceb126a9c"

