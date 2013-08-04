DESCRIPTION = "Rytec settings ${SATNAMES}"
MAINTAINER ?= "Mike Looijmans"
LICENSE = "CLOSED"

PR = "r1"

PACKAGES = "${PN}"

SRC_URI = "http://www.rytec.be/tools/Rytec_E2_${SATNAMES}_${RYTECSETTINGS_SRCDATE}.zip"

S = "${WORKDIR}/Rytec_E2_${SATNAMES}_${RYTECSETTINGS_SRCDATE}"

FILES_${PN} = "/etc/enigma2/*"

do_install() {
	install -d ${D}/etc/enigma2
	for f in lamedb bouquets.* userbouquet.*
	do
		install -m 644 ${f} ${D}/etc/enigma2/${f}
	done
}

