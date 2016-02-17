DESCRIPTION = "E2 HbbTV Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_${PN} = "vuplus-webkithbbtv-dumpait"

PV = "1.0"
PR = "20151228.r0"
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


SRC_URI[md5sum] = "d0b4dbbb074064ec17c9d76e639ddbc3"
SRC_URI[sha256sum] = "1aa76e29f7f850fd4744e639c63bd79e3b2b51a7e251f26e39e6cb21d2b48fb9"
