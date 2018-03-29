SRCDATE = "20180329"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "eafd368b3b9d54d69fb0648712fac046"
SRC_URI[sha256sum] = "f4d105b4fa55314fe84a376764dac5eff88b78eb7edca7e6a937e09b1b98ce30"
