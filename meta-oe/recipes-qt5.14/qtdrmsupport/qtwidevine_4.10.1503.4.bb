SUMMARY = "CDM pepper plugin Widevine"
DESCRIPTION = "Install widevine components for Qt"
LICENSE = "CLOSED"
DEPENDS = "qtwebengine"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI = "file://libwidevinecdm-${PV}_armhf.zip"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/usr
	install -d ${D}/usr/lib
	install -d ${D}/usr/lib/chromium
	install -m755 ${S}/libwidevinecdm.so ${D}/usr/lib/chromium
}

do_package_qa() {
}

FILES_${PN} = "/usr/lib/chromium"

RDEPENDS_${PN} += "qtwebengine"

INSANE_SKIP_${PN} += "ldflags already-stripped"
