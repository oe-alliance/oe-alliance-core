SUMMARY = "create Azboxhd patch.e2 images"
SECTION = "console/utils"
LICENSE = "CLOSED"

PROVIDES = "azbox-hd-buildimage"

PV="1.3"
PR = "r1"

SRC_URI = "file://pack_e2.c \
       file://Makefile.am \
       file://configure.ac"

S = "${WORKDIR}/pack_e2"

inherit autotools-brokensep native

do_configure_prepend() {
    install -m 0644 ${WORKDIR}/pack_e2.c ${S}
    install -m 0644 ${WORKDIR}/configure.ac ${S}
    install -m 0644 ${WORKDIR}/Makefile.am ${S}
}

BBCLASSEXTEND = "native nativesdk"

