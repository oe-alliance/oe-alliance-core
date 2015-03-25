SUMMARY = "OpenViX-HD THREE animated bootvideo"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PV = "1.0"
PR = "r1"

DEPENDS = "eplayer4"
RDEPENDS_${PN} = "eplayer4"

SRC_URI = "file://bootvideo.mp4"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/etc/enigma2
    install -m 0644 bootvideo.mp4 ${D}/etc/enigma2/bootvideo.mp4
}

SRC_URI[md5sum] = "34693FBF7FC06CF358420B6AADE3121B"
SRC_URI[sha256sum] = "89E528CF0FFD98A75EABF9FD8CF186D58618C19E1EC74917DB88333EB729AF6A"

FILES_${PN} = "/etc/enigma2"