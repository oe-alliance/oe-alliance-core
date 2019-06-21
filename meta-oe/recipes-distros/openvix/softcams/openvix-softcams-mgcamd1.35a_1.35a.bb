SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_enigma2-plugin-softcams-mgcamd135a = "libxcrypt-compat libcrypto-compat-0.9.7 zlib"

PR = "r6"

RREPLACES_enigma2-plugin-softcams-mgcamd135a += "enigma2-plugin-softcams-mgcamd"
RCONFLICTS_enigma2-plugin-softcams-mgcamd135a += "enigma2-plugin-softcams-mgcamd"

PACKAGES = "enigma2-plugin-softcams-mgcamd135a"

PROVIDES += "openvix-softcams-mgcamd135a"
RPROVIDES_enigma2-plugin-softcams-mgcamd135a += "openvix-softcams-mgcamd135a"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/mgcamd-1.35a.zip"

S = "${WORKDIR}/mgcamd-1.35a"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/mgcamd.mipsel ${D}/usr/softcams/mgcamd.${PV}
}

SRC_URI[md5sum] = "f444b9aa84d71cf7ca0d4397b92c091f"
SRC_URI[sha256sum] = "d49d9b40f4eba029a88068813b799966ca0b862f49c29b79960aa06d0c8570e9"

FILES_enigma2-plugin-softcams-mgcamd135a = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
