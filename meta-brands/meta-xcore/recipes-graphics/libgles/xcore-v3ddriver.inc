DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR = "r0-${SRCDATE}"

PROVIDES = "virtual/libgles2 virtual/egl"

DEPENDS = "patchelf-native"

SRC_URI = "https://source.mynonpublic.com/xcore/xcore-libgles-${MACHINE}-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}/libgles-${MACHINE}"

do_configure() {
}

do_compile() {
}

do_install:append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/lib/libv3dplatform.so ${D}${libdir}/
	install -m 0755 ${S}/lib/libv3ddriver.so ${D}${libdir}/
	patchelf --set-soname libv3ddriver.so ${D}${libdir}/libv3ddriver.so
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
	install -d ${D}/${includedir}
	install -m 0644 ${S}/include/v3dplatform.h ${D}${includedir}/
	for d in EGL GLES GLES2 KHR; do
		install -d ${D}${includedir}/$d
		for f in ${S}/include/$d/*.h; do
			install -m 0644 $f ${D}${includedir}/$d/
		done
	done
	install -d ${D}${libdir}/pkgconfig
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

FILES:${PN} = "/usr/lib/*"
FILES:${PN}-dev = "/usr/include/* ${libdir}/pkgconfig/*"

RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libv3dplatform.so libv3ddriver.so libGLESv2.so libEGL.so"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"

SRC_URI[md5sum] = "01a809640eed5ecd660bd147990a1793"
SRC_URI[sha256sum] = "ba2939f19f5976f5360ff81829cc1407627f6445fb6c115d4d16b9e68b35c008"
