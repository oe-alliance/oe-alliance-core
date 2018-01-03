DESCRIPTION = "MME image library"

require MastaG-apps.inc

EXTRA_OECONF_append = " \
	 --with-multicom=${STAGING_KERNEL_DIR}/multicom \
	"

DEPENDS += " fulan-dvb-modules-${MACHINE}"

FILES_${PN} += "${libdir}/libmme_host.so"
FILES_${PN}-dev = "${libdir}/libmme_host.la"

INSANE_SKIP_${PN} += "dev-so"
