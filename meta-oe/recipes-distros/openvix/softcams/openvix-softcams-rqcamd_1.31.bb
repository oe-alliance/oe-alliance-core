SUMMARY = "rqcamd ${PV} cardserver"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PR = "r2"

PACKAGES = "enigma2-plugin-softcams-rqcamd"

PROVIDES += "openvix-softcams-rqcamd"
RPROVIDES_enigma2-plugin-softcams-rqcamd += "openvix-softcams-rqcamd"

SRC_URI = "http://openvix.co.uk/feeds_extras/softcams/rqcamd-${PV}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install() {
    install -d ${D}/usr/softcams
    install -m 0755 ${S}/rqcamd.mips ${D}/usr/softcams/rqcamd
}

SRC_URI[md5sum] = "92443a7945d8d6d721b73ac3401dd136"
SRC_URI[sha256sum] = "0ae380c8e2a124843a82d106ec342c59836dbfd688a624012c18e3f26fb44ca5"

FILES_enigma2-plugin-softcams-rqcamd = "/usr"
INSANE_SKIP_${PN} = "already-stripped"
