DESCRIPTION = "frontpanel update"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"

PR = "r4"

PV = "${FPVERSION}"

SRC_URI = " \
	http://www.et-view.com/download/fpupdate-${FPUPDATE}.zip \
	http://www.et-view.com/download/avrmain-${MACHINE}-${FPVERSION}.zip;name=avrmain \
	"

S = "${WORKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "fpupdate"
INITSCRIPT_PARAMS = "start 22 S ."

FILES_${PN} = "/${bindir} /lib/firmware /etc/init.d"

do_install() {
	install -d ${D}/${bindir}
	install -d ${D}/lib/firmware
	install -m 0755 ${S}/fpupdate ${D}/${bindir}
	install -m 0644 ${S}/avrmain-* ${D}/lib/firmware/

	echo "#!/bin/sh" > ${S}/fpupdate.sh
	echo "boxtype=\`cat /proc/stb/info/boxtype\`" >> ${S}/fpupdate.sh
	echo "if ! [ -f /lib/firmware/avrmain-\$boxtype.hex ]; then" >> ${S}/fpupdate.sh
	echo "	exit 0" >> ${S}/fpupdate.sh
	echo "fi" >> ${S}/fpupdate.sh
	echo "if ! [ -x ${bindir}/fpupdate ]; then" >> ${S}/fpupdate.sh
	echo "	exit 0" >> ${S}/fpupdate.sh
	echo "fi" >> ${S}/fpupdate.sh
	echo "${bindir}/fpupdate /lib/firmware/avrmain-\$boxtype.hex ${FPVERSION} && rm /lib/firmware/avrmain*.hex" >> ${S}/fpupdate.sh

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
