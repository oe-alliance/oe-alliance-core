SUMMARY = "Dreambox box-specific configuration files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "PLi team"
require conf/license/license-gplv2.inc
PV = "1.0"
PR = "r3"

COMPATIBLE_MACHINE = "^(dm7020hd$|dm7020hdv2$|dm8000)$"

SRC_URI = "file://skin_box.xml"

PACKAGES = "${PN}"
FILES:${PN} = "/usr/share/enigma2"
inherit allarch
S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/usr/share/enigma2
    install -m 644 ${S}/skin_box.xml ${D}/usr/share/enigma2/
}
