SUMMARY = "Chromium and YoutubeTV"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

PV = "1.4"
PR = "${SRCDATE}"
SRC_URI = "https://source.mynonpublic.com/gigablue/chromium/chromium-gigablue-${MACHINE}-${SRCDATE}.tar.gz"

# Added very old NSS version need only for gigablue
PREFERRED_VERSION_nss = "3.17.3"

DEPENDS = "nss"
RDEPENDS:${PN} = "nspr nss libxslt libcurl libudev libcrypto-compat-1.0.0"
RDEPENDS:${PN} += "gb-v3ddriver-${MACHINE}"

do_install() {
	install -d ${D}/usr/local/chromium
	cp -afR ${UNPACKDIR}/chromium/* ${D}/usr/local/chromium/
	chmod 755 ${D}/usr/local/chromium/run.sh
	chmod 755 ${D}/usr/local/chromium/bin/browser_shell
	chmod 755 ${D}/usr/local/chromium/bin/chrome
	chmod 755 ${D}/usr/local/chromium/bin/chrome-sandbox
	chmod 755 ${D}/usr/local/chromium/bin/nxconfig
	install -d ${D}/usr/local/lib
	ln -s /usr/lib/libv3ddriver.so ${D}/usr/local/lib/libv3ddriver.so
}

do_package_qa() {
}

PROVIDES += "virtual/chromium-browser"
RPROVIDES:${PN} += "virtual-chromium-browser"
PROVIDES += "chromium-browser"
RPROVIDES:${PN} += "chromium-browser"

FILES:${PN} = "/"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
