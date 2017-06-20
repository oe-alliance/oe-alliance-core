SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
inherit allarch
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20160913"
PR = "r3"
SRC_URI = "http://source.mynonpublic.com/rytecepg/rytec.sources.xml.${PV}.gz \
    file://satmate.sources.xml \
    "

SRC_URI[md5sum] = "f3be792cd1cf1735fd97978dd16a4c15"
SRC_URI[sha256sum] = "06037068aba80e0a63e7adffd6478e3f2b98dc4bb369e31435d4d8bfed5d4348"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/epgimport"

do_install() {
    install -d ${D}/etc/epgimport
    install -m 644 ${S}/rytec.sources.xml.${PV} ${D}/etc/epgimport/rytec.sources.xml
    install -m 644 ${S}/satmate.sources.xml ${D}/etc/epgimport/
}
