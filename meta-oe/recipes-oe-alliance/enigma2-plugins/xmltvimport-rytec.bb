SUMMARY = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "oe-alliance"
PACKAGE_ARCH = "all"
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20151125"
PR = "r1"
SRC_URI = "http://rytecepg.ipservers.eu/epg_data/rytec.sources.${PV}.zip \
    file://satmate.sources.xml \
    "

SRC_URI[md5sum] = "55a391ef99b22617c759525063b0ea2d"
SRC_URI[sha256sum] = "591fe09304f2418866b48fe6049f18c2da7cb89068259c6cbca6f3237c570cff"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-xmltvimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/xmltvimport"

do_install() {
    install -d ${D}/etc/xmltvimport
    install -m 644 ${S}/rytec.sources.xml ${D}/etc/xmltvimport/
    install -m 644 ${S}/satmate.sources.xml ${D}/etc/xmltvimport/
}
