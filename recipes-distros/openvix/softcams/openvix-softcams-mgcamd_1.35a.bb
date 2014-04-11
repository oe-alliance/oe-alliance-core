SUMMARY = "mgcamd ${PV} softcam"
LICENSE = "CLOSED"

RDEPENDS_${PN} = "libcrypto-compat-0.9.7"

PR = "r3"

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

SRC_URI[md5sum] = "9625bffdefdf227973b31f56705d22a7"
SRC_URI[sha256sum] = "8a54987be9a295935418450fe6d05bd01846cc69de4267c11af2902340bb66f5"

FILES_enigma2-plugin-softcams-mgcamd = "/usr"