SUMMARY = "openViX bootlogo Helios pack2"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r0"

SRC_URI = "http://enigma2.world-of-satellite.com/bootlogos/openvix-helios3.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/engima2
    install -m 0644 bootlogo.mvi ${D}/etc/engima2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/engima2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/engima2/radio.mvi
}

SRC_URI[md5sum] = "4b56ed17947193d75f6cf20b971492eb"
SRC_URI[sha256sum] = "4a9427e64e906835988061093d9f1ca0bbd8f4d85d2798fe7ee96d5fc38e547e"

FILES = "/etc"