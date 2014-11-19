SUMMARY = "ViX-HD animated bootlogo"
LICENSE = "CLOSED"

PV = "2.3"
PR = "r2"

DEPENDS = "eplayer4"
RDEPENDS_${PN} = "eplayer4"

SRC_URI = "file://bootvideo.mp4"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 bootvideo.mp4 ${D}/usr/share/bootvideo.mp4
}

SRC_URI[md5sum] = "34693FBF7FC06CF358420B6AADE3121B"
SRC_URI[sha256sum] = "89E528CF0FFD98A75EABF9FD8CF186D58618C19E1EC74917DB88333EB729AF6A"

FILES_${PN} = "/usr/share"