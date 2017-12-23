PV = "20101009"
PR = "r0"
SUMMARY = "Run some scripts earlier than others"
PACKAGES = "${PN}"

require conf/license/license-gplv2.inc

SRC_URI = "file://${PN}.sh"

inherit update-rc.d

INITSCRIPT_NAME = "${PN}.sh"
INITSCRIPT_PARAMS = "start 15 S ."

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 ${WORKDIR}/${PN}.sh ${D}/etc/init.d/${PN}.sh
}
