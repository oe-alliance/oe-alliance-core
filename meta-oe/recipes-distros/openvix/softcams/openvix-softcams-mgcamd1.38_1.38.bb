SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_enigma2-plugin-softcams-mgcamd138 = "libcrypto-compat-0.9.7 libxcrypt-compat zlib"

PR = "r5"

RREPLACES_enigma2-plugin-softcams-mgcamd138 += "enigma2-plugin-softcams-mgcamd"
RCONFLICTS_enigma2-plugin-softcams-mgcamd138 += "enigma2-plugin-softcams-mgcamd"

PACKAGES = "enigma2-plugin-softcams-mgcamd138"

PROVIDES += "openvix-softcams-mgcamd138"
RPROVIDES_enigma2-plugin-softcams-mgcamd138 += "openvix-softcams-mgcamd138"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/mgcamd-1.38.zip"

S = "${WORKDIR}/mgcamd-1.38"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/mgcamd.mipsel ${D}/usr/softcams/mgcamd.${PV}
}

SRC_URI[md5sum] = "cf98d4662516313f88617ef6e77afa9d"
SRC_URI[sha256sum] = "98af0d95f25b40db6178cb47cf84205ee0d1a5418d15fc80540a7fd0eecad301"

FILES_enigma2-plugin-softcams-mgcamd138 = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
