SUMMARY = "openhdf-spinner"
MAINTAINER = "HDFreaks"
SECTION = "base"
LICENSE = "proprietary"
require conf/license/license-gplv2.inc

inherit allarch

PV = "1.0"
PR = "r4"

SRC_URI = "file://spinners/"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/spinners/*.png ${D}${datadir}/enigma2/spinner
}

FILES:${PN} = "${datadir}/enigma2"
