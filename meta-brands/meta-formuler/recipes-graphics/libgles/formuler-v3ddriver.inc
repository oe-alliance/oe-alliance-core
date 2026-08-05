DESCRIPTION = "V3DDRIVER for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "virtual/libgles2 virtual/egl"
DEPENDS = "formuler-v3ddriver-headers patchelf-native"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/formuler/${MACHINE}-v3ddriver-${SRCDATE}.zip"

S = "${UNPACKDIR}"

do_configure() {
}

do_compile() {
}

do_install:append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/libnexus.so ${D}${libdir}/
	install -m 0755 ${S}/libv3ddriver.so ${D}${libdir}/
	patchelf --set-soname libv3ddriver.so ${D}${libdir}/libv3ddriver.so
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
	install -d ${D}${libdir}/pkgconfig
	cat > ${D}${libdir}/pkgconfig/nxpl.pc <<EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: NXPL
Description: Broadcom Nexus platform layer
Version: ${PV}
Libs: -L${libdir} -lnxpl -lnexus
Cflags: -I${includedir}
EOF
}

FILES:${PN} = "/usr/lib/*"
FILES:${PN}-dev = "/usr/include/* ${libdir}/pkgconfig/*"

RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libnxpl.so libnexus.so libv3ddriver.so libGLESv2.so libEGL.so"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
