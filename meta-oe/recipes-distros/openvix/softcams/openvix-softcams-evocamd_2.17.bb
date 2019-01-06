SUMMARY = "evocamd ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r2"

RDEPENDS_enigma2-plugin-softcams-evocamd = "libcrypto-compat-0.9.7 zlib"

PACKAGES = "enigma2-plugin-softcams-evocamd"

PROVIDES += "openvix-softcams-evocamd"
RPROVIDES_enigma2-plugin-softcams-evocamd += "openvix-softcams-evocamd"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/evocamd-${PV}.zip"

S = "${WORKDIR}/evocamd-${PV}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/evocamd.mips ${D}/usr/softcams/evocamd
}

SRC_URI[md5sum] = "74972fae77137f91b014b9cf4b8da137"
SRC_URI[sha256sum] = "cc4b190afc8ecbb4664cf297c6298638e28c55e92b381e631dc3729c45bdada6"

FILES_enigma2-plugin-softcams-evocamd = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
