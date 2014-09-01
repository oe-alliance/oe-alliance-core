DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPEND += "jpeg"

do_install_append () {
	install -d ${D}${includedir}/mmeimage
	install -m 644 ${S}/libmmeimage/*.h ${D}${includedir}/mmeimage
}

FILES_${PN}-dev += "${includedir}/mmeimage"

