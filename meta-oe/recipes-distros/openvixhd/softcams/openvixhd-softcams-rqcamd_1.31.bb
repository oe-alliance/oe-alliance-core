SUMMARY = "rqcamd ${PV} cardserver"
LICENSE = "CLOSED"

PR = "r0"

PACKAGES = "enigma2-plugin-softcams-rqcamd"

PROVIDES += "openvixhd-softcams-rqcamd"
RPROVIDES_enigma2-plugin-softcams-rqcamd += "openvixhd-softcams-rqcamd"

SRC_URI = "http://enigma2.world-of-satellite.com/git-extras/rqcamd-${PV}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/rqcamd.mips ${D}/usr/softcams/rqcamd
}

SRC_URI[md5sum] = "92443a7945d8d6d721b73ac3401dd136"
SRC_URI[sha256sum] = "0ae380c8e2a124843a82d106ec342c59836dbfd688a624012c18e3f26fb44ca5"

FILES_enigma2-plugin-softcams-rqcamd = "/usr"

