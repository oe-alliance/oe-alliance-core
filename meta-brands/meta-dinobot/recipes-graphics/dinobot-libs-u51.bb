SRCDATE = "20180418"

inherit pkgconfig

do_install_append() {
	install -d ${D}${libdir}/pkgconfig
	cp -r ${WORKDIR}/pkgconfig/*.pc ${D}${libdir}/pkgconfig/
}

require dinobot-libs.inc

SRC_URI[md5sum] = "0530281e594ce03cfc2f6d54d919cfce"
SRC_URI[sha256sum] = "bc477e224592a0e5d1a951138ebd6997e7ae987e62fe91c635320f0275214249"
