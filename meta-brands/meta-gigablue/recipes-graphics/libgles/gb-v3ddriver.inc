DESCRIPTION = "V3DDRIVER for ${MACHINE_DRIVER}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "virtual/libgles2 virtual/egl"
DEPENDS = "gb-v3ddriver-headers patchelf-native"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/gigablue/v3ddriver/${MACHINE_DRIVER}-v3ddriver-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

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
}

FILES:${PN} = "/usr/lib/*"
FILES:${PN}-dev = "/usr/include/*"

RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libnexus.so libnxclient.so libnxpl.so libv3ddriver.so libGLESv2.so libEGL.so"

INSANE_SKIP:${PN} += "already-stripped dev-so ldflags"
