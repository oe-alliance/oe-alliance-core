SUMMARY = "Chromium and YoutubeTV"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

PV = "1.1"
PR = "${SRCDATE}-r0"

SRC_URI = "http://code.vuplus.com/download/chromium/chromium-vuplus_${MACHINE}_${SRCDATE}.tar.gz \
           file://run.patch;striplevel=0"

# Added very old NSS version need only for vuplus
PREFERRED_VERSION_nss = "3.17.3"

DEPENDS = "nss"
RDEPENDS_${PN} = "nspr nss libxslt libcurl libudev python-subprocess"

S = "${WORKDIR}/chromium"

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

PROVIDES += "virtual/chromium-browser"
RPROVIDES_${PN} += "virtual/chromium-browser"
PROVIDES += "chromium-browser"
RPROVIDES_${PN} += "chromium-browser"

FILES_${PN} = "/"

INSANE_SKIP_${PN} += "already-stripped"
