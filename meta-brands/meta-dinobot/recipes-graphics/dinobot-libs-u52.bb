SRCDATE = "20180425"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "531754dc34b9071c00a5eed5420f7c70"
SRC_URI[sha256sum] = "efd58cdb3aabd8afad9c37c0a84c96b4446290c63161cadbc4fc528a9399befa"
