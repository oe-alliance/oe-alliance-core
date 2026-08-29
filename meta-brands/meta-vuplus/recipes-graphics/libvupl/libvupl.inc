DESCRIPTION = "Vu+ API for EGL/OpenGLES"
SECTION = "libs"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINEBUILD}"

DEPENDS = "vuplus-libgles-${MACHINEBUILD}"
RDEPENDS:${PN} = "vuplus-libgles-${MACHINEBUILD}"

PROVIDES += "libvupl"
RPROVIDES:${PN} += "libvupl"

PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = "https://source.mynonpublic.com/vuplus/release/libvupl/libvupl-${PV}-${MACHINEBUILD}-${PR}.tar.gz"

S = "${UNPACKDIR}/libvupl-${PV}-${MACHINEBUILD}"

inherit pkgconfig

do_install() {
	install -d ${D}${includedir}
	install -m 644 ${S}/include/* ${D}${includedir}/
	install -d ${D}${libdir}
	install -m 755 ${S}/lib/*.so ${D}${libdir}/
	install -d ${D}${libdir}/pkgconfig
	cp -r ${S}/lib/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
	sed -i -e 's/^Requires:.*/Requires: libgles nxpl/' ${D}${libdir}/pkgconfig/libvupl.pc
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

INSANE_SKIP:${PN} = "ldflags"
