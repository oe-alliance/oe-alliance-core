DESCRIPTION = "V3DDRIVER for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "virtual/libgles2 virtual/egl"
DEPENDS = "et-v3ddriver-headers patchelf-native"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/xtrend/${MACHINE_DRIVER}-v3ddriver-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_configure() {
}

do_compile() {
}

do_install:append() {
	install -d ${D}${libdir}
	install -m 0755 ${S}/libnxpl.so ${D}${libdir}/
	install -m 0755 ${S}/libnexus.so ${D}${libdir}/
	install -m 0755 ${S}/libv3ddriver.so ${D}${libdir}/
	install -m 0755 ${S}/libnxclient.so ${D}${libdir}/
	patchelf --set-soname libv3ddriver.so ${D}${libdir}/libv3ddriver.so
	ln -s libv3ddriver.so ${D}${libdir}/libEGL.so
	ln -s libv3ddriver.so ${D}${libdir}/libGLESv2.so
}

FILES:${PN} = "/usr/lib/*"
FILES:${PN}-dev = "/usr/include/*"

RPROVIDES:${PN} = "virtual-libgles2 virtual-egl libnxpl.so libnexus.so libv3ddriver.so libnxclient.so libGLESv2.so libEGL.so"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped dev-so"
