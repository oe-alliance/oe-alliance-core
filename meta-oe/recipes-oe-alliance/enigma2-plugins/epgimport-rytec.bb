SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
inherit allarch
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20210319"
PR = "r0"
SRC_URI = "file://rytec.sources.xml file://russian.sources.xml file://spainKoala.sources.xml \
    "

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/epgimport"

do_install() {
    install -d ${D}/etc/epgimport
    install -m 644 ${S}/rytec.sources.xml ${D}/etc/epgimport/rytec.sources.xml
    install -m 644 ${S}/russian.sources.xml ${D}/etc/epgimport/russian.sources.xml
    install -m 644 ${S}/spainKoala.sources.xml ${D}/etc/epgimport/spainKoala.sources.xml
}
