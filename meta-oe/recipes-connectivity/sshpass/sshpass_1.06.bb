SUMMARY = "sshpass"
LICENSE = "GPLv2+"

LIC_FILES_CHKSUM = "file://main.c;beginline=1;endline=19;md5=5f0246a83a3118d9161abc9611abea7b"

SRC_URI = "http://downloads.sourceforge.net/project/sshpass/sshpass/${PV}/sshpass-${PV}.tar.gz"

inherit autotools-brokensep

S = "${WORKDIR}/sshpass-${PV}"

do_compile() {
    oe_runmake
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/sshpass-${PV}/sshpass ${D}${bindir}/sshpass
}

FILES_${PN} = "${bindir}/sshpass"

SRC_URI[md5sum] = "f59695e3b9761fb51be7d795819421f9"
SRC_URI[sha256sum] = "c6324fcee608b99a58f9870157dfa754837f8c48be3df0f5e2f3accf145dee60"

BBCLASSEXTEND = "native"
