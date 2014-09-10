SUMMARY = "evocamd ${PV} softcam"
LICENSE = "CLOSED"

PR = "r0"

PACKAGES = "enigma2-plugin-softcams-evocamd"

PROVIDES += "openvixhd-softcams-evocamd"
RPROVIDES_enigma2-plugin-softcams-evocamd += "openvixhd-softcams-evocamd"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/evocamd-${PV}.zip"

S = "${WORKDIR}/evocamd-${PV}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/evocamd.mips ${D}/usr/softcams/evocamd
}

SRC_URI[md5sum] = "74972fae77137f91b014b9cf4b8da137"
SRC_URI[sha256sum] = "cc4b190afc8ecbb4664cf297c6298638e28c55e92b381e631dc3729c45bdada6"

FILES_enigma2-plugin-softcams-evocamd = "/usr"