DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r0"

do_install:append () {
	install -d ${D}${includedir}/libmmeimage
	install -m 644 ${S}/libmmeimage/*.h ${D}${includedir}/libmmeimage
}

FILES:${PN} += "${libdir}/libmmeimage.so"
FILES:${PN}-dev = "${includedir}/libmmeimage ${libdir}/libmmeimage.la"

INSANE_SKIP:${PN} += "dev-so"
