DESCRIPTION = "Hoeba settings Ziggo/Casema West"
MAINTAINER ?= "Mike Looijmans"

require conf/license/openpli-gplv2.inc

SRC_URI = "file://*"

PR = "r0"

PACKAGES = "${PN}"
PROVIDES="virtual/enigma2-settings"

FILES_${PN} = "/etc/enigma2/*"
S = "${WORKDIR}"

do_install() {
	install -d ${D}/etc/enigma2
	for f in services bouquets* userbouquet*
	do
		install -m 644 ${f} ${D}/etc/enigma2/${f}
	done
}

inherit allarch
