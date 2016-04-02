DESCRIPTION = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "MiLo, rytec @ pli-images.org"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

inherit allarch

PV = "20151125"
# http://epgalfasite.dyndns.tv/ resolves to:
SRC_URI = "http://rytecepg.ipservers.eu/epg_data/rytec.sources.${PV}.zip"
S = "${WORKDIR}"

PACKAGES = "${PN}"

FILES_${PN} = "/etc/epgimport"

do_install() {
	install -d ${D}/etc/epgimport
	install -m 644 ${S}/rytec.sources.xml ${D}/etc/epgimport/rytec.sources.xml
}

SRC_URI[md5sum] = "55a391ef99b22617c759525063b0ea2d"
SRC_URI[sha256sum] = "591fe09304f2418866b48fe6049f18c2da7cb89068259c6cbca6f3237c570cff"

