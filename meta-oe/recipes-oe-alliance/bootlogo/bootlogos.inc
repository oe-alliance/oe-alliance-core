require conf/license/license-gplv2.inc

RDEPENDS_${PN} += "showiframe"

inherit allarch

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/enigma2
    install -m 0644 bootlogo.mvi ${D}/etc/enigma2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/enigma2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/enigma2/radio.mvi
}

FILES_${PN} = "/etc"