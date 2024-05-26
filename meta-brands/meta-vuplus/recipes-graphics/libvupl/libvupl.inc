DESCRIPTION = "Vu+ API for EGL/OpenGLES"
SECTION = "libs"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

RDEPENDS:${PN} = "vuplus-libgles-${MACHINE}"

PROVIDES += "libvupl"
RPROVIDES:${PN} += "libvupl"

PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/libvupl/libvupl-${PV}-${MACHINE}-${PR}.tar.gz"

S = "${WORKDIR}/libvupl-${PV}-${MACHINE}"

inherit pkgconfig

do_install() {
	install -d ${D}${includedir}
	install -m 644 ${S}/include/* ${D}${includedir}/
	install -d ${D}${libdir}
	install -m 755 ${S}/lib/*.so ${D}${libdir}/
	install -d ${D}${libdir}/pkgconfig
	cp -r ${S}/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

INSANE_SKIP:${PN} = "ldflags"
