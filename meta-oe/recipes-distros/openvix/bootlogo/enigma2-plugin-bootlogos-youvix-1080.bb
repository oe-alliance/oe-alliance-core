SUMMARY = "openViX-HD bootlogo"
LICENSE = "CLOSED"

PV = "1.0"
PR = "r1"

SRC_URI = "file://openvix-bootlogos1080-youvix.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/enigma2
    install -m 0644 bootlogo.mvi ${D}/etc/enigma2/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/etc/enigma2/backdrop.mvi
    install -m 0644 radio.mvi ${D}/etc/enigma2/radio.mvi
}

SRC_URI[md5sum] = "C2751D7C29DDB2D9829D03D8B9B920D3"
SRC_URI[sha256sum] = "BFF90177FF32A4509ED7180A641C2F197F09221D72A8611BACE5B69B286608DA"

FILES_${PN} = "/etc/enigma2"