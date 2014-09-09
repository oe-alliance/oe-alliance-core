SUMMARY = "openViX-HD bootlogo"
LICENSE = "CLOSED"

PV = "2.3"
PR = "r1"

SRC_URI = "file://openvixhd-vixhd.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootlogo.mvi ${D}/usr/share/bootlogo.mvi
    install -m 0644 backdrop.mvi ${D}/usr/share/backdrop.mvi
    install -d ${D}/usr/share/enigma2/skin_default
    install -m 0644 radio.mvi ${D}/usr/share/enigma2/skin_default/radio.mvi
}

SRC_URI[md5sum] = "8cf095009dd1dbd84aecf19082782713"
SRC_URI[sha256sum] = "5b8d6a615a1a364b49c33d37ed0f1f406ff8dcb19c6d6c12c2cbdc54b7abe85b"

FILES_${PN} = "/usr/share"