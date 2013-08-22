DESCRIPTION = "rtmpdump Real-Time Messaging Protocol"

require conf/license/license-gplv2.inc


DEPENDS = "openssl"

PV = "2.3"
PR = "r1"

SRC_URI ="http://rtmpdump.mplayerhq.hu/download/rtmpdump-${PV}.tgz"

SRC_URI[md5sum] = "eb961f31cd55f0acf5aad1a7b900ef59"
SRC_URI[sha256sum] = "ef38b7a99d82ce6912063d21063aeaf28185341b3df486e24bffce5354224b2c"

S = "${WORKDIR}/rtmpdump-2.3"

do_compile() {
	make CROSS_COMPILE=${TARGET_PREFIX}
}


do_install() {
	install -d ${D}${bindir}
        install rtmpdump ${D}${bindir}/
}
