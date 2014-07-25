SUMMARY = "GigaBlue software wol"
MAINTAINER = "GigaBlue"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

SRC_URI = "file://gigablue_wol"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/gigablue_wol ${D}/usr/bin/gigablue_wol
}
