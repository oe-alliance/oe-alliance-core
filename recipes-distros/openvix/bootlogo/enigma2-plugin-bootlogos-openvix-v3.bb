SUMMARY = "openViX 3.0 bootlogo"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r1"

SRC_URI = "http://enigma2.world-of-satellite.com/bootlogos/openvix-v3.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/engima2
    install -m 0644 bootlogo.mvi ${D}/etc/engima2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/engima2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/engima2/radio.mvi
}

SRC_URI[md5sum] = "b91e91d6f982a683f6cb4230f41a24a7"
SRC_URI[sha256sum] = "1fdfede19320d6c9099aaa3a2643ea06b304c9d56d23485817a79c8f172a32e7"

FILES_${PN} = "/etc"