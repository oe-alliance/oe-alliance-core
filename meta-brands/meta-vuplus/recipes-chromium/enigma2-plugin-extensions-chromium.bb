DESCRIPTION = "E2 Chromium Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "20160516_r1"
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

SRC_URI[md5sum] = "463adfde95d9c0677adcb89b4bb1b9da"
SRC_URI[sha256sum] = "efaa40792935e240f2d91b9fe3bf4d28835fadef62e28d2ac1fb260d9ed23903"

