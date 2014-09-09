SUMMARY = "Enigma2 busy spinner"
MAINTAINER = "OpenViX-HD"

require conf/license/license-gplv2.inc

inherit allarch

RREPLACES_${PN} += "enigma2-spinner"
RCONFLICTS_${PN} += "enigma2-spinner"

PV = "2.3"
PR = "r1"

SRC_URI = "file://spinner.zip"

S = "${WORKDIR}/"

FILES_${PN} = "${datadir}/enigma2"

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}
