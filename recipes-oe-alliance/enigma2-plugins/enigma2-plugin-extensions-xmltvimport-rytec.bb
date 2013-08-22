DESCRIPTION = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "oe-allaince"
PACKAGE_ARCH = "all"
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20130806"
PR = "r2"

SRC_URI = "file://sourcelist file://satmate.sources.xml"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-xmltvimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/xmltvimport"

do_install() {
	install -d ${D}/etc/xmltvimport
	install -m 644 ${S}/sourcelist ${D}/etc/xmltvimport/
	install -m 644 ${S}/satmate.sources.xml ${D}/etc/xmltvimport/
}
