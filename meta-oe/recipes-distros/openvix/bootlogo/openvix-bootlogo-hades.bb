SUMMARY = "openViX bootlogo Hades"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r1"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-hades.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2/skin_default
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/skin_default/radio.mvi
}

SRC_URI[md5sum] = "d10bdd79d7f09a792272c2427df8ecea"
SRC_URI[sha256sum] = "dbb257234085f856eacd827a6c22a6f5f290b1b46abb028a7f87a07b74929eb8"

FILES_${PN} = "/usr/share"