DESCRIPTION = "frontpanel update for Venton HDx"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.1"
PR = "r1"

FPVERSION = "15"
FPUPDATE = "1.1"

PV = "${FPVERSION}"

S = "${WORKDIR}/"

INI3000BIN = "RHS300_micom.bin"
INI5000BIN = "RHS500_micom.bin"
INI7000BIN = "RHS700_micom.bin"

SRC_URI = "file://${INI3000BIN} file://${INI5000BIN} file://${INI7000BIN} file://fpupdate"


do_install() {
	install -d ${D}/${bindir}
	install -d ${D}/etc/fpupdate
        install -m 0644 ${INI3000BIN} ${D}/etc/fpupdate/
        install -m 0644 ${INI7000BIN} ${D}/etc/fpupdate/
        install -m 0644 ${INI5000BIN} ${D}/etc/fpupdate/

	install -d ${D}/bin
	install -m 0755 ${S}/fpupdate ${D}/bin/
}


PACKAGE_ARCH := "${MACHINE_ARCH}"
