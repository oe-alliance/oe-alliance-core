SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
inherit allarch
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20190610"
PR = "r0"
SRC_URI = "file://rytec.sources.xml \
    "

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES_${PN} = "${sysconfdir}/epgimport"

do_install() {
    install -d ${D}${sysconfdir}/epgimport
    install -m 644 ${S}/rytec.sources.xml ${D}${sysconfdir}/epgimport/rytec.sources.xml
}
