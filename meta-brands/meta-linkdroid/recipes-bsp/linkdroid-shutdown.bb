DESCRIPTION = "amlogic power and reboot tools"
MAINTAINER = "amiko"
SECTION = "base"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-gplv2.inc

PV = "1.0"
PR = "r3"

SRC_URI = " file://shutdown.sh \
    file://amlreboot \
    file://amlhalt \
"

S = "${WORKDIR}"


inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}/etc/init.d/
    install -m 0755 ${WORKDIR}/shutdown.sh ${D}/etc/init.d/${PN}
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/amlreboot ${D}${bindir}/
    install -m 0755 ${WORKDIR}/amlhalt ${D}${bindir}/
}

FILES_${PN} = "${bindir} ${sysconfdir}"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 08"

do_rm_work() {
}