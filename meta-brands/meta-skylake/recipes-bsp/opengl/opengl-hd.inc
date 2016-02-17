DESCRIPTION = "GLES/EGL libraries"

LICENSE = "CLOSED"
require conf/license/license-close.inc

#PROVIDES = "virtual/egl"

DEPENDS = "opengl-headers-hd"

PR = "r0"

SRC_URI = " \
		file://libv3ddriver.so \
		file://libnxpl.so \
		"

S = "${WORKDIR}"

do_configure () {
}

do_compile () {
}

do_install_append() {
	install -d ${D}${libdir}
	install -m 0755 libv3ddriver.so ${D}${libdir}/
	install -m 0755 libnxpl.so ${D}${libdir}/
	ln -s ./libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s ./libv3ddriver.so ${D}${libdir}/libGLESv2.so
}

FILES_${PN} = "/usr/lib/"
FILES_${PN}-dev = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"
INSANE_SKIP_${PN} = "dev-so"
