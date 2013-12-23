DESCRIPTION="Sogno mac checker - flasher"
LICENSE = "Gpl2"
LICENSE = "CLOSED"

PR = "r4"
PV = "0.1"

SRC_URI="file://sogno_mac_check \
	file://sogno-mac-check.sh \
"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults"


do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${WORKDIR}/sogno_mac_check ${D}/usr/bin/sogno_mac_check
	install -d ${D}/etc/init.d
	install -m 0755 ${WORKDIR}/${PN}.sh ${D}/etc/init.d/${PN}
}
	
PACKAGE_ARCH = "${MACHINE_ARCH}"
 
