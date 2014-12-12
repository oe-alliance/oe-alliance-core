SUMMARY = "openViX bootlogo Hades"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r1"

SRC_URI = "http://vixhades-files.kicks-ass.org/rossi2000/bootlogos/openvix-hades.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2/skin_default
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/skin_default/radio.mvi
}

SRC_URI[md5sum] = "D10BDD79D7F09A792272C2427DF8ECEA"
SRC_URI[sha256sum] = "DBB257234085F856EACD827A6C22A6F5F290B1B46ABB028A7F87A07B74929EB8"

FILES_${PN} = "/usr/share"