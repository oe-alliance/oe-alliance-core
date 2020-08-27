SUMMARY = "openhdf-spinner"
MAINTAINER = "HDFreaks"
SECTION = "base"
LICENSE = "proprietary"
require conf/license/license-gplv2.inc

inherit allarch

PV = "1.0"
PR = "r3"

SRC_URI = "file://spinners/"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}

FILES_${PN} = "${datadir}/enigma2"
