SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"
require conf/license/license-close.inc

RDEPENDS_enigma2-plugin-softcams-mgcamd145c = "libxcrypt libcrypto-compat-0.9.7 zlib"

PR = "r0"

RREPLACES_enigma2-plugin-softcams-mgcamd145c += "enigma2-plugin-softcams-mgcamd"
RCONFLICTS_enigma2-plugin-softcams-mgcamd145c += "enigma2-plugin-softcams-mgcamd"

PACKAGES = "enigma2-plugin-softcams-mgcamd145c"

PROVIDES += "openvix-softcams-mgcamd145c"
RPROVIDES_enigma2-plugin-softcams-mgcamd145c += "openvix-softcams-mgcamd145c"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/mgcamd-1.45c.zip"

S = "${WORKDIR}/mgcamd-1.45c"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/mgcamd.mipsel ${D}/usr/softcams/mgcamd.${PV}
}

SRC_URI[md5sum] = "521d7bacc8ffc6ca07f487cdb4b36e57"
SRC_URI[sha256sum] = "6e3a35493b6760afe055eb001efcaf391e5976e6d0cfb1b346bd50592a343db5"

FILES_enigma2-plugin-softcams-mgcamd145c = "/usr"
INSANE_SKIP_${PN} = "already-stripped"

