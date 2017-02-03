DESCRIPTION = "Webkit browser for HbbTV"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "${SRCDATE}"
SRC_URI = "http://archive.vuplus.com/download/build_support/webkit-hbbtv-browser_${MACHINE}_${SRCDATE}.tar.gz"

RDEPENDS_${PN} = "webkit-gtk"

do_install_append() {
	install -d ${D}/usr/bin
	install -d ${D}/usr/lib/mozilla/plugins
	install -d ${D}/home/root

	install -m 0755 ${WORKDIR}/webkit-hbbtv-browser/none.html ${D}/home/root
	install -m 0755 ${WORKDIR}/webkit-hbbtv-browser/browser ${D}/usr/bin
	install -m 0755 ${WORKDIR}/webkit-hbbtv-browser/libhbbtvplugin.so ${D}/usr/lib/mozilla/plugins/
}

do_package_qa() {
}

PROVIDES += "webkit-hbbtv-browser"
RPROVIDES_${PN} += "webkit-hbbtv-browser"

PACKAGE_ARCH := "${MACHINE_ARCH}"

FILES_${PN} = "/"

