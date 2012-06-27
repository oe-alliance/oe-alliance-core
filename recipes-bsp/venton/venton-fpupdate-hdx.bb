DESCRIPTION = "frontpanel update for Venton HDx"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

FPVERSION = "15"
FPUPDATE = "1.0"

PV = "${FPVERSION}"

S = "${WORKDIR}/"

inherit update-rc.d

INITSCRIPT_NAME = "fpupdate"
INITSCRIPT_PARAMS = "start 22 S ."
INI3000BIN = "RHS300_micom.bin"
INI5000BIN = "RHS500_micom.bin"
INI7000BIN = "RHS700_micom.bin"

SRC_URI = "file://${INI3000BIN} file://${INI5000BIN} file://${INI7000BIN} file://fpupdate.sh"


do_install() {
	install -d ${D}/${bindir}
	install -d ${D}/etc/fpupdate
        install -m 0644 ${INI3000BIN} ${D}/etc/fpupdate/
        install -m 0644 ${INI7000BIN} ${D}/etc/fpupdate/
        install -m 0644 ${INI5000BIN} ${D}/etc/fpupdate/

	install -d ${D}/etc/init.d
	install -m 0755 ${S}/fpupdate.sh ${D}/etc/init.d/fpupdate
}

pkg_postinst_${PN}_append() {
	if test -z "$D"
	then
		# force update without requiring reboot
		# (update-rc.d does not restart the script, when it was already installed)
		/etc/init.d/fpupdate
	fi
	true
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
