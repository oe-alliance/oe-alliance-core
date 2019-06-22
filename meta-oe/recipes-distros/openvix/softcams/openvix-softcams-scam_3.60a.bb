SUMMARY = "scam ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_enigma2-plugin-softcams-scam = "libcrypto-compat-0.9.7 libxcrypt-compat"

PR = "r2"

PACKAGES = "enigma2-plugin-softcams-scam"

PROVIDES += "openvix-softcams-scam"
RPROVIDES_enigma2-plugin-softcams-scam += "openvix-softcams-scam"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/scam-v${PV}.zip"

S = "${WORKDIR}/scam-v${PV}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/scam.mipsel ${D}/usr/softcams/scam
}

SRC_URI[md5sum] = "186ade5145fdaeec1a8b0699b5dead6a"
SRC_URI[sha256sum] = "c8d328d6f4623a70970902cc417416236cccb60dd5c0e5808ae43b77e9e30110"

FILES_enigma2-plugin-softcams-scam = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
