SUMMARY = "sshpass"
LICENSE = "GPLv2+"
PR = "r1"

LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=19;md5=bb3cd21c281c2ac32d4cb77974a88ae2"

SRC_URI = "http://sourceforge.net/projects/sshpass/files/sshpass/${PV}/${PN}-${PV}.tar.gz"

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

SRC_URI[md5sum] = "f59695e3b9761fb51be7d795819421f9"
SRC_URI[sha256sum] = "c6324fcee608b99a58f9870157dfa754837f8c48be3df0f5e2f3accf145dee60"

BBCLASSEXTEND = "native"
