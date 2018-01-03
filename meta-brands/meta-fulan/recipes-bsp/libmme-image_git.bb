DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r2"

FILES_${PN} += "${libdir}/libmmeimage.so"
FILES_${PN}-dev = "${includedir}/libmmeimage ${libdir}/libmmeimage.la"

INSANE_SKIP_${PN} += "dev-so"
