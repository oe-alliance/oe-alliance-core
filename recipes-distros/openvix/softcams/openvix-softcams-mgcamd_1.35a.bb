SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"

RDEPENDS_${PN} = "libcrypto-compat-0.9.7"

PR = "r2"

PACKAGES = "enigma2-plugin-softcams-mgcamd"

PROVIDES += "openvix-softcams-mgcamd"
RPROVIDES_enigma2-plugin-softcams-mgcamd += "openvix-softcams-mgcamd"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/mgcamd-${PV}.zip"

S = "${WORKDIR}//mgcamd-${PV}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/mgcamd.mipsel ${D}/usr/softcams/mgcamd
}

SRC_URI[md5sum] = "e8aae7d4f54ec01316c527ae8fcbd71e"
SRC_URI[sha256sum] = "54872f453801f5fa102940b9cc8234461f82cc3817eeafbc19ad2089a4ca7291"

FILES_enigma2-plugin-softcams-mgcamd = "/usr"