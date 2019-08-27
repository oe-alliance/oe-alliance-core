SUMMARY = "Dreambox box-specific configuration files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "PLi team"
require conf/license/license-gplv2.inc
PV = "1.0"
PR = "r3"

SRC_URI = "file://skin_box.xml"

PACKAGES = "${PN}"
FILES_${PN} = "/usr/share/enigma2"
inherit allarch
S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share/enigma2
    install -m 644 ${S}/skin_box.xml ${D}/usr/share/enigma2/
}
