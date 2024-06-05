SUMMARY = "Flash pepper plugin Adobe"
DESCRIPTION = "Install flash components for Qt"
LICENSE = "CLOSED"
DEPENDS = "qtwebengine"
PACKAGE_ARCH := "${MACHINE_ARCH}"

SRC_URI = "file://libflashplayer-${PV}_armhf.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d ${D}/usr
	install -d ${D}/usr/lib
	install -d ${D}/usr/lib/PepperFlash
	install -m755 ${S}/libpepflashplayer.so ${D}/usr/lib/PepperFlash
}

do_package_qa() {
}

FILES:${PN} = "/usr/lib/PepperFlash"

RDEPENDS:${PN} += "qtwebengine"

INSANE_SKIP:${PN} += "ldflags already-stripped"
