SRCDATE = "20180412"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "f3ab846341358fc5468159f83b7d9cb8"
SRC_URI[sha256sum] = "44b57a22cb26739dad02c041f6f2b7d521335e6c374c2cd8f3d52891ae5e5a5d"
