SUMMARY = "openViX-HD bootlogo"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "2.3"
PR = "r1"

SRC_URI = "file://openvixhd-apollo.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2/skin_default
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/skin_default/radio.mvi
}

SRC_URI[md5sum] = "C2751D7C29DDB2D9829D03D8B9B920D3"
SRC_URI[sha256sum] = "BFF90177FF32A4509ED7180A641C2F197F09221D72A8611BACE5B69B286608DA"

FILES_${PN} = "/usr/share"