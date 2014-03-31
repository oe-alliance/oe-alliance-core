DESCRIPTION="blackbox7405 mac checker - flasher"
LICENSE = "Gpl2"
LICENSE = "CLOSED"

PR = "r5"
PV = "0.1"

SRC_URI="file://blackbox7405_mac_check \
	file://blackbox7405-mac-check.sh \
"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults"


do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${WORKDIR}/blackbox7405_mac_check ${D}/usr/bin/blackbox7405_mac_check
	install -d ${D}/etc/init.d
	install -m 0755 ${WORKDIR}/${PN}.sh ${D}/etc/init.d/${PN}
}
	
PACKAGE_ARCH = "${MACHINE_ARCH}"
 
