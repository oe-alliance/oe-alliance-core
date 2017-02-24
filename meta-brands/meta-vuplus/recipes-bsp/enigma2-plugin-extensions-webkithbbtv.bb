DESCRIPTION = "E2 HbbTV Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_${PN} = "vuplus-webkithbbtv-dumpait webkit-hbbtv-browser"

PV = "1.0"
PR = "20170105.r0"
SRC_URI = "http://archive.vuplus.com/download/build_support/webkit-hbbtv-plugin_${PR}.tar.gz"

S = "${WORKDIR}"

do_install_append() {
    install -d ${D}/usr/bin
    install -d ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebkitHbbTV

    install -m 0755 ${WORKDIR}/webkit-hbbtv-plugin/run-webkit.sh ${D}/usr/bin
    cp -aRf ${WORKDIR}/webkit-hbbtv-plugin/WebkitHbbTV/* ${D}/usr/lib/enigma2/python/Plugins/Extensions/WebkitHbbTV/
}

do_package_qa() {
}

PROVIDES += "enigma2-plugin-extensions-webkithbbtv"
RPROVIDES_${PN} += "enigma2-plugin-extensions-webkithbbtv"

FILES_${PN} = "/"


SRC_URI[md5sum] = "8b93c2f013934658daa2162cf58e3b71"
SRC_URI[sha256sum] = "b6322cbfc06abe19052cfff0ae1f76d00d6a6f185fdc09fd10085823f9203c26"
