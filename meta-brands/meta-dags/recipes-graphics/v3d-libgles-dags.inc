DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR="${SRCDATE}.${SRCDATE_PR}"

PROVIDES = "virtual/libgles2 virtual/egl"

SRC_URI = "http://en3homeftp.net/pub/src/libgles-arm-${PV}-${PR}.tar.gz"

S = "${WORKDIR}/libgles"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/usr/lib/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/usr/lib/libv3ddriver.so ${D}${libdir}/
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
	for d in EGL GLES GLES2 KHR VG; do
		install -d ${D}${includedir}/$d
		for f in ${S}/include/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
}

FILES_${PN} = "/usr/lib/*"
FILES_${PN}-dev = "/usr/include/*"

RPROVIDES_${PN} = "libnxpl.so libv3ddriver.so libGLESv2.so libEGL.so"

INSANE_SKIP_${PN} += "already-stripped dev-so"

