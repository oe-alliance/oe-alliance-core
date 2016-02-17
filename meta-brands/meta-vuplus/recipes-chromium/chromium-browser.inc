DESCRIPTION = "Chromium and YoutubeTV"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "${SRCDATE}"
SRC_URI = "http://code.vuplus.com/download/chromium/chromium-vuplus_${MACHINE}_${SRCDATE}.tar.gz"

DEPENDS_${PN} = "nss"
RDEPENDS_${PN} = "nspr nss"

do_install() {
	install -d ${D}/usr/local/chromium
	cp -afR ${WORKDIR}/chromium/* ${D}/usr/local/chromium/
	chmod 755 ${D}/usr/local/chromium/run.sh
	chmod 755 ${D}/usr/local/chromium/bin/browser_shell
	chmod 755 ${D}/usr/local/chromium/bin/chrome
	chmod 755 ${D}/usr/local/chromium/bin/chrome-sandbox
	chmod 755 ${D}/usr/local/chromium/bin/goexit
}

do_package_qa() {
}

PROVIDES += "chromium-browser"
RPROVIDES_${PN} += "chromium-browser"

PACKAGE_ARCH := "${MACHINE_ARCH}"

FILES_${PN} = "/"

INHIBIT_PACKAGE_STRIP = "1"

