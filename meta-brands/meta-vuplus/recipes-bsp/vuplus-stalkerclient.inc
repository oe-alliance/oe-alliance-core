DESCRIPTION = "Vu+ Staker Client Plugin"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

PV = "0.3"
PR = "${SRCDATE}"
SRC_URI = "http://source.mynonpublic.com/vuplus/stalkerclient-plugin-${PV}-${MACHINE}-${PR}.tar.gz"

do_install() {
	install -d ${D}${libdir}/enigma2/python/Plugins/Extensions/StalkerClient
	cp -rp ${WORKDIR}/plugin-extensions-stalkerclient/* ${D}${libdir}/enigma2/python/Plugins/Extensions/StalkerClient/
}

do_package_qa() {
}

FILES_${PN} += "${libdir}/enigma2/python/Plugins/Extensions/StalkerClient/*"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
