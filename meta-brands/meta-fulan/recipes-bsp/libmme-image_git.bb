DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r2"

do_install_append () {
	install -d ${D}${includedir}/libmme_image
	install -m 644 ${S}/*.h ${D}${includedir}/libmme_image
}

FILES_${PN} += "${libdir}/libmmeimage.so"
FILES_${PN}-dev = "${includedir}/libmme_image ${libdir}/libmmeimage.la"

INSANE_SKIP_${PN} += "dev-so"
