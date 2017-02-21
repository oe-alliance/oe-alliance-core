SUMMARY = "WS-Discovery daemon"
DESCRIPTION = "Anounces a device to Windows via WSD"
LICENSE = "GPLv2"

require conf/license/license-gplv2.inc

DEPENDS = "util-linux samba"

SRC_URI = " \
    file://Makefile \
    file://wsdd.c \
    "

PR = "r1"
PV = "1.0"

S = "${WORKDIR}"

do_compile() {
    make -f Makefile
}

do_install() {
    install -d ${D}/${sbindir}
    install -m 755 ${S}/wsdd ${D}/${sbindir}
}
