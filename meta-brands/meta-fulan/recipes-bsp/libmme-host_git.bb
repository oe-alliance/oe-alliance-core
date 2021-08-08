DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += " fulan-dvb-modules-${MACHINE}"

FILES:${PN} += "${libdir}/libmme_host.so"
FILES:${PN}-dev = "${libdir}/libmme_host.la"

INSANE_SKIP:${PN} += "dev-so"
