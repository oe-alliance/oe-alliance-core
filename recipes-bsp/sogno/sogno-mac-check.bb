DESCRIPTION="Sogno mac checker - flasher"
LICENSE = "Gpl2"
require conf/license/openpli-gplv2.inc

PR = "r3"
PV = "0.1"

SRC_URI="file://sogno_mac_check \
    file://sogno-mac-check.sh \
    "

S = "${WORKDIR}"

inherit autotools update-rc.d

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults"

FILES_${PN} = "/"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/sogno_mac_check ${D}/usr/bin/sogno_mac_check
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/${PN}.sh ${D}/etc/init.d/${PN}
    }
 
