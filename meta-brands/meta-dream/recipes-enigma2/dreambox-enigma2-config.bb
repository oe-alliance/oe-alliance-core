SUMMARY = "Dreambox box-specific configuration files"
SECTION = "base"
PRIORITY = "required"
MAINTAINER = "PLi team"
LICENSE = "WTFPL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1b71aebc25e7533ebaa5b320a20e7ed2"
PV = "1.0"
PR = "r3"

SRC_URI = "file://skin_box.xml file://LICENSE"

PACKAGES = "${PN}"
FILES_${PN} = "/usr/share/enigma2"
PACKAGE_ARCH = "all"
S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share/enigma2
    install -m 644 ${S}/skin_box.xml ${D}/usr/share/enigma2/
}
