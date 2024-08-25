SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "${PKGDATE}"

PROVIDES += "kodiegl"
RPROVIDES:${PN} += "kodiegl"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/kodi/KodiGLESPL_${MACHINE}_${PKGDATE}.tar.gz"

S = "${WORKDIR}/KodiGLESPL_${MACHINE}"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/bin/* ${D}/usr/bin

	install -d ${D}/usr/lib
	install -m 0755 ${S}/lib/*.so ${D}/usr/lib

	install -d ${D}/usr/include
	install -m 0755 ${S}/include/*.h ${D}/usr/include
}

do_package_qa() {
}

INSANE_SKIP:${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"

FILES:${PN}="${libdir} ${bindir}"
FILES:${PN}-dev = "${includedir}"

PACKAGE_ARCH = "${MACHINE}"
