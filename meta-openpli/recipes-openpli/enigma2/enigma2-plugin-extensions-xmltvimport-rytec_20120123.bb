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

SRC_URI[md5sum] = "307f1f09f787a2df3c802dbaeb096d13"
SRC_URI[sha256sum] = "b86fce9aa833bc65130a896834000c801bf0290f2005b3f146a9290bb9a24724"
