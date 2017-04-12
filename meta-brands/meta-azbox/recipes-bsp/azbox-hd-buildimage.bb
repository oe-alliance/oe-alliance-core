SUMMARY = "create Azboxhd patch.e2 images"
SECTION = "console/utils"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV="1.3"
PR = "r2"

SRC_URI = "file://pack_e2.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} -o pack_e2 pack_e2.c
}

do_install() {
    install -d ${D}/${bindir}/
    install -m 0755 ${S}/pack_e2 ${D}/${bindir}/
}

BBCLASSEXTEND = "native"
