DESCRIPTION = "GLES/EGL files for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc

PR = "r0-${SRCDATE}"

PROVIDES = "virtual/libgles2 virtual/egl"

SRC_URI = "http://source.mynonpublic.com/xcore/xcore-libgles-${MACHINE}-${SRCDATE}.tar.gz"

S = "${WORKDIR}/libgles-${MACHINE}"

do_configure() {
}

do_compile() {
}

do_install_append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/lib/libv3dplatform.so ${D}${libdir}/
	install -m 0755 ${S}/lib/libv3ddriver.so ${D}${libdir}/
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
}

FILES_${PN} = "/usr/lib/*"
FILES_${PN}-dev = "/usr/include/*"

RPROVIDES_${PN} = "virtual/libgles2 virtual/egl libv3dplatform.so libv3ddriver.so libGLESv2.so libEGL.so"

INSANE_SKIP_${PN} += "already-stripped dev-so"

SRC_URI[md5sum] = "01a809640eed5ecd660bd147990a1793"
SRC_URI[sha256sum] = "ba2939f19f5976f5360ff81829cc1407627f6445fb6c115d4d16b9e68b35c008"