DESCRIPTION = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "MiLo, rytec @ pli-images.org"
PACKAGE_ARCH = "all"

require conf/license/openpli-gplv2.inc

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

SRC_URI[md5sum] = "9f9d4b783f88dbb6486ac2bddd148626"
SRC_URI[sha256sum] = "4e0087145cd71acb2026e31e8a9ed3f025c20879e03c57f75169f45c6d45d67d"
