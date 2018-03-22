SRCDATE = "20180318"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "d0fa6179fdbb31e30ac3ec889e856f08"
SRC_URI[sha256sum] = "fc3ec42d5b1e2ecb3a7131cf363747402479e219dab56680f1f0e5e6ed36be63"
