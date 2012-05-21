PV = "20101009"
PR = "r0"
DESCRIPTION = "Run some scripts earlier than others"
PACKAGES = "${PN}"

require conf/license/openpli-gplv2.inc

SRC_URI = "file://${PN}.sh"


do_install() {
	install -d ${D}/etc/rcS.d
	install -m 0755 ${WORKDIR}/${PN}.sh ${D}/etc/rcS.d/S15${PN}.sh
}
