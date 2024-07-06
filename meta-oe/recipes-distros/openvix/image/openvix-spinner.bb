SUMMARY = "OpenViX Spinner"
MAINTAINER = "OpenViX"
inherit allarch

require conf/license/license-gplv2.inc

RREPLACES:${PN} += "enigma2-spinner"
RCONFLICTS:${PN} += "enigma2-spinner"

PV = "${IMAGE_VERSION}"
PR = "r0"

SRC_URI = "file://spinner.tgz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

FILES:${PN} = "${datadir}/enigma2"

do_install() {
    install -d ${D}${datadir}/enigma2/spinner
    install -m 0644 ${S}/*.png ${D}${datadir}/enigma2/spinner
}
