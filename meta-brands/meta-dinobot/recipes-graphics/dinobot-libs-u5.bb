SRCDATE = "20180420"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "22bf6c2f7591da00797b70de8590da76"
SRC_URI[sha256sum] = "86b1cae54f958be77d0a8d5d4a73149384310f33eef8d65c63db938115123c0d"
