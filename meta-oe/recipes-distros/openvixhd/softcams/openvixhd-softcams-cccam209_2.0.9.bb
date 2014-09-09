SUMMARY = "CCcam ${PV} softcam"
LICENSE = "CLOSED"

PR = "r2"

PACKAGES = "enigma2-plugin-softcams-cccam209"

PROVIDES += "openvixhd-softcams-cccam209"
RPROVIDES_enigma2-plugin-softcams-cccam209 += "openvixhd-softcams-cccam209"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/CCcam-${PV}.zip"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/CCcam.${TARGET_ARCH} ${D}/usr/softcams/CCcam.${PV}
}

SRC_URI[md5sum] = "978899af06fd8f5fcdda2a74a890dd16"
SRC_URI[sha256sum] = "33e3e253c4658f797def7621aafbd17115549bc25ac476ac52c6eccc923066a1"

FILES_enigma2-plugin-softcams-cccam209 = "/usr"