SUMMARY = "Rytec's sources and channels for the XMLTV importer"
MAINTAINER = "oe-alliance"
PACKAGE_ARCH = "all"
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20160308"
PR = "r2"
SRC_URI = "http://rytecepg.ipservers.eu/epg_data/rytec.sources.xml.${PV}.gz \
    file://satmate.sources.xml \
    "

SRC_URI[md5sum] = "e23973ddadc095828c9fc0d0a1581ab4"
SRC_URI[sha256sum] = "bace6cd1dd9696e927e831ab46c58bbc18a9029714343caabd83c5470dea80c3"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-xmltvimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/xmltvimport"

do_install() {
    install -d ${D}/etc/xmltvimport
    install -m 644 ${S}/rytec.sources.xml.${PV} ${D}/etc/xmltvimport/rytec.sources.xml
    install -m 644 ${S}/satmate.sources.xml ${D}/etc/xmltvimport/
}
