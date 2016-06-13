SUMMARY = "Rytec's sources and channels for the EPG importer"
MAINTAINER = "oe-alliance"
PACKAGE_ARCH = "all"
LICENSE = "WTFPL"

require conf/license/license-gplv2.inc

PV = "20160328"
PR = "r3"
SRC_URI = "http://source.mynonpublic.com/rytecepg/rytec.sources.xml.${PV}.gz \
    file://satmate.sources.xml \
    "

SRC_URI[md5sum] = "7b279de913911a22a32b088c174df7d5"
SRC_URI[sha256sum] = "c14f9bb2a21a64e2ad34afa743c9607917bf47eb275111a774cced280867942d"

S = "${WORKDIR}"

DEPENDS = "enigma2-plugin-extensions-epgimport"
PACKAGES = "${PN}"

FILES_${PN} = "/etc/epgimport"

do_install() {
    install -d ${D}/etc/epgimport
    install -m 644 ${S}/rytec.sources.xml.${PV} ${D}/etc/epgimport/rytec.sources.xml
    install -m 644 ${S}/satmate.sources.xml ${D}/etc/epgimport/
}
