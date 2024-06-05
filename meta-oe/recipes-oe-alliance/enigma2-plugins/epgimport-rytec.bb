SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
inherit allarch
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20230401"
PR = "r0"
SRC_URI = "file://rytec.sources.xml file://russian.sources.xml file://spainKoala.sources.xml \
    "

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES:${PN} = "/etc/epgimport"

do_install() {
    install -d ${D}/etc/epgimport
    install -m 644 ${UNPACKDIR}/rytec.sources.xml ${D}/etc/epgimport/rytec.sources.xml
    install -m 644 ${UNPACKDIR}/russian.sources.xml ${D}/etc/epgimport/russian.sources.xml
    install -m 644 ${UNPACKDIR}/spainKoala.sources.xml ${D}/etc/epgimport/spainKoala.sources.xml
}
