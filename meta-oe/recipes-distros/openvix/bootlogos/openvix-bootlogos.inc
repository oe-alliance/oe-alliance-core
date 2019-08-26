require conf/license/license-gplv2.inc
inherit allarch

RDEPENDS_${PN} += "showiframe"

INHIBIT_PACKAGE_STRIP = "1"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/enigma2
    install -m 0644 bootlogo.mvi ${D}${sysconfdir}/enigma2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}${sysconfdir}/enigma2/backdrop.mvi
    install -m 0644 radio.mvi ${D}${sysconfdir}/enigma2/radio.mvi
}

FILES_${PN} = "${sysconfdir}"