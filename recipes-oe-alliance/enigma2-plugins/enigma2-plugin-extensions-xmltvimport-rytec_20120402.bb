DESCRIPTION = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "MiLo, rytec @ pli-images.org"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit allarch

PR = "r0"

SRC_URI = "http://www.rytec.be/tools/rytec.sources.xml.${PV}.zip"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-xmltvimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/epgimport"

do_install() {
	install -d ${D}/etc/epgimport
	for f in ${S}/*.xml
	do
		install -m 644 ${f} ${D}/etc/epgimport/
	done
}

SRC_URI[md5sum] = "a5cfb9032a7b7578747f0d2e791cb476"
SRC_URI[sha256sum] = "0e1eb3b1e4be7e4b70d5cb341a6a5acb1cb79c17e6d33f110f1dfef32c2b7b7b"
