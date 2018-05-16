DESCRIPTION = "Create NIM sockets"
MAINTAINER = "mnigma"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit update-rc.d

PV = "0"
PR = "r1"

#INHIBIT_PACKAGE_STRIP = "1"

SRC_URI = "file://lkdosth \
           file://lkdosth.init \
"

S = "${WORKDIR}"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/lkdosth ${D}${bindir}/
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/lkdosth.init ${D}${sysconfdir}/init.d/${PN}
}

FILES_${PN} = "${bindir} ${sysconfdir}"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 08"

do_rm_work() {
}

