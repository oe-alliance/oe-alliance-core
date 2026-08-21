DESCRIPTION = "V3DDRIVER for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "virtual/libgles2 virtual/egl"
DEPENDS = "vuplus-v3ddriver-headers patchelf-native"

PV = "${SRCDATE}"
PR = "r1"

SRC_URI = "https://source.mynonpublic.com/vuplus.de/v3ddriver/libgles-${MACHINE}-v3ddriver-${SRCDATE}.zip"

S = "${UNPACKDIR}"

do_configure() {
}

do_compile() {
}

do_install:append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/v3ddriver/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/v3ddriver/libv3ddriver.so ${D}${libdir}/
	install -m 0755 ${S}/v3ddriver/libnexus.so ${D}${libdir}/
	install -m 0755 ${S}/v3ddriver/libnxclient.so ${D}${libdir}/
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
Libs: -L${libdir} -lnxpl -lnxclient -lnexus
Cflags: -I${includedir}
EOF
}

FILES:${PN} = "/usr/lib/*"
FILES:${PN}-dev = "/usr/include/* ${libdir}/pkgconfig/*"

RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libnexus.so libnxclient.so libnxpl.so libv3ddriver.so libGLESv2.so libEGL.so"

INSANE_SKIP = "32bit-time"
INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
