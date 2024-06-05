SUMMARY = "GigaBlue software wol"
MAINTAINER = "GigaBlue"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r2"

SRC_URI = "file://gigablue_wol"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/gigablue_wol ${D}/usr/bin/gigablue_wol
}
