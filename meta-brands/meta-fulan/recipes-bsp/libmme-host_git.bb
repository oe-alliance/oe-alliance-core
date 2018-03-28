DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += " fulan-dvb-modules-${MACHINE}"

FILES_${PN} += "${libdir}/libmme_host.so"
FILES_${PN}-dev = "${libdir}/libmme_host.la"

INSANE_SKIP_${PN} += "dev-so"
