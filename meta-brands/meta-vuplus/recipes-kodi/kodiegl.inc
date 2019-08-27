SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "${PKGDATE}"

PROVIDES += "kodiegl"
RPROVIDES_${PN} += "kodiegl"

SRC_URI = "http://archive.vuplus.com/download/build_support/kodi/KodiGLESPL_${MACHINE}_${PKGDATE}.tar.gz"

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

INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN}="${libdir} ${bindir}"
FILES_${PN}-dev = "${includedir}"

PACKAGE_ARCH = "${MACHINE}"

