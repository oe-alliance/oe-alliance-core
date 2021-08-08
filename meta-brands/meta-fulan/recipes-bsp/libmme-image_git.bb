DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r2"

FILES:${PN} += "${libdir}/libmmeimage.so"
FILES:${PN}-dev = "${includedir}/libmmeimage ${libdir}/libmmeimage.la"

INSANE_SKIP:${PN} += "dev-so"
