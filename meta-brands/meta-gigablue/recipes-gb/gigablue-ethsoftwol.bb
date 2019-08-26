SUMMARY = "GigaBlue software wol"
MAINTAINER = "GigaBlue"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r2"

SRC_URI = "file://gigablue_wol"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/gigablue_wol ${D}${bindir}/gigablue_wol
}
