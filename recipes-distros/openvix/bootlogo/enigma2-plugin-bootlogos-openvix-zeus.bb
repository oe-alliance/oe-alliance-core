SUMMARY = "openViX bootlogo Zeus"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r2"

SRC_URI = "http://enigma2.world-of-satellite.com/bootlogos/openvix-zeus.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/enigma2
    install -m 0644 bootlogo.mvi ${D}/etc/enigma2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/enigma2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/enigma2/radio.mvi
}

SRC_URI[md5sum] = "25bb4a9526350cfaa28cfa9fe288eebe"
SRC_URI[sha256sum] = "30b68be08383209f831aa035df8acb0da2500e36f9c8d2ef786d049a7dadacd3"

FILES_${PN} = "/etc"