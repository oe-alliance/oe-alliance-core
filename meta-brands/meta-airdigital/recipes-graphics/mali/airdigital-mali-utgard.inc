DESCRIPTION = "Mali OpenGL"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += " virtual/libgles2 virtual/libgles1 virtual/egl virtual/libopencl"
RPROVIDES_${PN} = "virtual/libgles2 virtual/libgles1 virtual/egl virtual/libopencl libmali.so libEGL.so libGLESv1_CM.so libGLESv2.so"
DEPENDS = "airdigital-mali-utgard-headers"

SRC_URI = "http://source.mynonpublic.com/zgemma/${MACHINE}-mali-${SRCDATE}.zip"

S = "${WORKDIR}"

do_configure() {
}

do_compile() {
}

do_install_append() {
	install -d ${D}${libdir}
	install -d ${D}${sysconfdir}/udev/rules.d
	echo 'KERNEL=="mali0", MODE="0660", GROUP="video"' > ${S}/50-mali.rules
	install -m 0755 ${S}/libMali.so ${D}${libdir}
	ln -sf libMali.so ${D}${libdir}/libmali.so
	ln -sf libMali.so ${D}${libdir}/libEGL.so
	ln -sf libMali.so ${D}${libdir}/libGLESv1_CM.so
	ln -sf libMali.so ${D}${libdir}/libGLESv2.so
	install -m 0644 ${S}/50-mali.rules ${D}${sysconfdir}/udev/rules.d/50-mali.rules
}

FILES_${PN} = "/usr/lib/* /etc/udev/rules.d/50-mali.rules"
FILES_${PN}-dev = "/usr/include/*"

INSANE_SKIP_${PN} += "already-stripped dev-so ldflags"