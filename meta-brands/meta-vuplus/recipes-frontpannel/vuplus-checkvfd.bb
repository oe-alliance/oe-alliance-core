SUMMARY = "check vfd firmware"
MAINTAINER = "vuplus team"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r2"
SRC_REV = ""

SRC_URI = "file://checkvfd "

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FirmwareUpgrade
    install -m 0755 ${WORKDIR}/checkvfd ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/FirmwareUpgrade
}

PACKAGES = "${PN}"
FILES_${PN} = "/"

