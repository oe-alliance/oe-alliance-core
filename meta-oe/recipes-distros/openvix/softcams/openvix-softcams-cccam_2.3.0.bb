SUMMARY = "CCcam ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r4"

RDEPENDS_enigma2-plugin-softcams-cccam = "libxcrypt-compat"

PACKAGES = "enigma2-plugin-softcams-cccam"

PROVIDES += "openvix-softcams-cccam"
RPROVIDES_enigma2-plugin-softcams-cccam += "openvix-softcams-cccam"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/CCcam-${PV}.zip"

S = "${WORKDIR}/CCcam-${PV}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}/usr/softcams/CCcam.${PV}
}

SRC_URI[md5sum] = "befff8f25c30dd2a1e18b8885ee0f119"
SRC_URI[sha256sum] = "6b461d95987b7333dfae51280205cd92558bd04c7ef488e37b058c8652201bdf"

FILES_enigma2-plugin-softcams-cccam = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
