DESCRIPTION="blackbox7405 mac checker - flasher"
LICENSE = "Gpl2"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r6"
PV = "0.1"

SRC_URI="file://blackbox7405_mac_check \
	file://blackbox7405-mac-check.sh \
"
S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

inherit pkgconfig update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults"

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${UNPACKDIR}/blackbox7405_mac_check ${D}/usr/bin/blackbox7405_mac_check
	install -d ${D}/etc/init.d
	install -m 0755 ${UNPACKDIR}/${PN}.sh ${D}/etc/init.d/${PN}
}
	
PACKAGE_ARCH = "${MACHINE_ARCH}"
 
