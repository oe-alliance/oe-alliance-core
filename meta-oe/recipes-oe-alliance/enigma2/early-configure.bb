PV = "20101009"
PR = "r0"
SUMMARY = "Run some scripts earlier than others"
PACKAGES = "${PN}"

require conf/license/license-gplv2.inc

SRC_URI = "file://${PN}.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/${PN}.sh ${D}${sysconfdir}/rcS.d/S15${PN}.sh
}
