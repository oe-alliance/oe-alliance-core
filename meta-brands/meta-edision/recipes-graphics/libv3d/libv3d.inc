DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "libs"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PROVIDES = "virtual/libgles2 virtual/egl"
RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libv3dplatform.so libv3ddriver.so libGLESv2.so libEGL.so"

DEPENDS = "patchelf-native"

SRC_URI = "https://source.mynonpublic.com/edision/edision-libv3d-${PV}.tar.xz"

S = "${UNPACKDIR}/edision-libv3d-${PV}"

ARCH_DIR:aarch64 = "lib64"
ARCH_DIR:arm = "lib"

FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/lib*${SOLIBSDEV}"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT  = "1"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

INSANE_SKIP:${PN} += "file-rdeps"

do_install() {
	install -m 0755 -d ${D}${libdir}
	oe_soinstall ${S}/${ARCH_DIR}/libv3dplatform.so.${PV} ${D}${libdir}
	oe_soinstall ${S}/${ARCH_DIR}/libv3ddriver.so.${PV} ${D}${libdir}
	patchelf --set-soname libv3ddriver.so ${D}${libdir}/libv3ddriver.so
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
	install -m 0755 -d ${D}${libdir}/pkgconfig
	cat > ${D}${libdir}/pkgconfig/v3dplatform.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: v3dplatform
Description: Broadcom V3D platform layer
Version: ${PV}
Libs: -L${libdir} -lv3dplatform
Cflags: -I${includedir}
EOF
}

FILES:${PN}-dev += "${includedir}/* ${libdir}/pkgconfig/*"
