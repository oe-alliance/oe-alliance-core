SUMMARY = "vfdctl is a little utility to control the display"
MAINTAINER = "OpenATV Team"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "all"

require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r0"

SRC_URI = "file://vfdctl.c"


do_compile () {
    ${CC} ${CFLAGS} ${LDFLAGS} ${WORKDIR}/vfdctl.c -o vfdctl
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 vfdctl ${D}${bindir}/
}
