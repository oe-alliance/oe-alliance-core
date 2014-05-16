SUMMARY = "openViX bootlogo Helios pack2"
LICENSE = "CLOSED"

inherit allarch

PV = "1.0"
PR = "r4"

SRC_URI = "http://enigma2.world-of-satellite.com/bootlogos/openvix-helios3.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/enigma2
    install -m 0644 bootlogo.mvi ${D}/etc/enigma2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/enigma2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/enigma2/radio.mvi
}

SRC_URI[md5sum] = "b7fdb0ed77c4ac3ff516ed90e0bacd06"
SRC_URI[sha256sum] = "7ff1535ce8fda9048f016d1bb772062100a3422cf2dcc43a9bae70a2ea42d902"

FILES_${PN} = "/etc"