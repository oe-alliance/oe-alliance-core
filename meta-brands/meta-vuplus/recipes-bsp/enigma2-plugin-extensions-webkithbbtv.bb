DESCRIPTION = "E2 HbbTV Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "20151006.r1"
SRC_URI = "http://archive.vuplus.com/download/build_support/webkit-hbbtv-plugin_${PR}.tar.gz"

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


SRC_URI[md5sum] = "3dcc2cf9379849ce472113b90b31f1ea"
SRC_URI[sha256sum] = "cde2a763dea1b124e9b3f14aeccb5b2cd43407eef4a088f844d59478b6340e75"
