DESCRIPTION = "V3DDRIVER for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "virtual/libgles2 virtual/egl"
RPROVIDES_${PN} = "libgles2 egl"
DEPENDS = "hd-v3ddriver-headers"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI = "http://downloads.mutant-digital.net/v3ddriver/${MACHINE}-v3ddriver-${SRCDATE}.zip"

S = "${WORKDIR}"

do_configure() {
}

do_compile() {
}

do_install_append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/libnexus.so ${D}${libdir}/
	install -m 0755 ${S}/libv3ddriver.so ${D}${libdir}/
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
}

FILES_${PN} = "/usr/lib/*"
FILES_${PN}-dev = "/usr/include/*"

INSANE_SKIP_${PN} += "already-stripped dev-so"