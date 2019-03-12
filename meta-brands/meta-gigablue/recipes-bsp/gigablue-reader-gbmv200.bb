SUMMARY = "libreader for Gigablue Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(gbmv200)$"

SRCDATE = "20190307"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/gigablue/mv200/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "090208994c05084952d7f3d56dd0e1ec"
SRC_URI[sha256sum] = "edfa6e58e8aa81f35ebce3b13ff48880cd28d55256f0571c2f4c15ce96aed39f"
