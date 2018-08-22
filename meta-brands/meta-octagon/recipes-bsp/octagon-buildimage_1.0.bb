SUMMARY = "Creat the usb ugrade file"
LICENSE = "CLOSED"
require conf/license/license-close.inc

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/zhtq/mkupdate.git;protocol=git \
"

S = "${WORKDIR}/${PN}"

CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

inherit native

do_configure[noexec] = "1"

do_compile () {
        cp ${WORKDIR}/git/*.c ${S}/.
        cp ${WORKDIR}/git/*.h ${S}/.
        cp ${WORKDIR}/git/Makefile ${S}/.
        oe_runmake 
}

do_install () {
        install -d ${D}/${sbindir}
        install -m 775 ${S}/mkupdate ${D}/${sbindir}
}
