DESCRIPTION = "ViX sources and channels for the XMLTV importer"
MAINTAINER = "AndyBlac, rytec"
PACKAGE_ARCH = "all"

require conf/license/openvix-gplv2.inc

PR = "r0"

SRC_URI = " file://vix.sources.xml"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-xmltvimport"

FILES_${PN} = "/etc/epgimport"

do_install() {
	install -d ${D}/etc/epgimport
	install -m 644 ${S}/vix.sources.xml ${D}/etc/epgimport/
}
