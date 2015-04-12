SUMMARY = "openViX bootlogo Hades"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r4"

SRC_URI = "http://openvix.co.uk/feeds_extras/bootlogos/openvix-hades.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2/skin_default
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/skin_default/radio.mvi
}

SRC_URI[md5sum] = "32baa551a39b270f282030dad8d42b28"
SRC_URI[sha256sum] = "1abf50c2b8b89779ae70416f5811aa2d60ac856f6aa62fe94654e0ff35b2bba3"

FILES_${PN} = "/usr/share"