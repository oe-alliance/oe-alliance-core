SUMMARY = "sshpass"
LICENSE = "GPLv2+"
PR = "r1"

LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=19;md5=bb3cd21c281c2ac32d4cb77974a88ae2"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PV}/${PN}-${PV}.tar.gz"

inherit autotools-brokensep

S = "${WORKDIR}/${PN}-${PV}"

do_compile() {
    oe_runmake
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/${PN}-${PV}/sshpass ${D}${bindir}/sshpass
}

FILES_${PN} = "${bindir}/sshpass"

SRC_URI[md5sum] = "2e15ebb088507e3ae968e41a55954c51"
SRC_URI[sha256sum] = "c18a7f27e03afc20f42718768e6dcdccaa88ce011ace32359cd832e28bfb9b5c"

BBCLASSEXTEND = "native"
