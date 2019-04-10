DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "libs"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PROVIDES = "virtual/libgles2 virtual/egl"

SRC_URI = "http://source.mynonpublic.com/edision/libv3d-${MACHINE}-${PV}.tar.xz"

S = "${WORKDIR}/libv3d-osmio4k-${PV}"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/lib*${SOLIBSDEV}"
INSANE_SKIP_${PN} = "ldflags dev-so"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT  = "1"

do_install() {
	install -m 0755 -d ${D}${libdir}
	oe_soinstall ${S}/lib/libv3dplatform.so.${PV} ${D}${libdir}
	oe_soinstall ${S}/lib/libv3ddriver.so.${PV} ${D}${libdir}
	ln -s libv3ddriver.so.${PV} ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so.${PV} ${D}${libdir}/libGLESv2.so
	install -m 0755 -d ${D}/${includedir}
	install -m 0644 ${S}/include/v3dplatform.h ${D}${includedir}
	for d in EGL GLES GLES2 GLES3 KHR; do
		install -m 0755 -d ${D}${includedir}/$d
		for f in ${S}/include/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d
		done
	done
}
