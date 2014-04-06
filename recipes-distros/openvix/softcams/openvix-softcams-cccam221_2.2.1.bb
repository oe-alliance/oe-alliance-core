SUMMARY = "CCcam ${PV} softcam"
LICENSE = "CLOSED"

PR = "r3"

PACKAGES = "enigma2-plugin-softcams-cccam221"

PROVIDES += "openvix-softcams-cccam221"
RPROVIDES_enigma2-plugin-softcams-cccam221 += "openvix-softcams-cccam221"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/CCcam-${PV}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}/usr/softcams/CCcam.${PV}
}

SRC_URI[md5sum] = "b44bc062cd04670bfb1f91061cd20864"
SRC_URI[sha256sum] = "52427fb384e918c984c10dda6a8b1f2130eed9b523344ef052ab26c185fc4953"

FILES_enigma2-plugin-softcams-cccam221 = "/usr"