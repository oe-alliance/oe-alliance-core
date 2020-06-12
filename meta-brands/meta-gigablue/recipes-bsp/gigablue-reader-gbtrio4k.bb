SUMMARY = "libreader for Gigablue Model ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(gbmv200)$"

SRCDATE = "20200227"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "abdbfd7d2ab005b22187bb387c913e33"
SRC_URI[sha256sum] = "befde16a36de1bb29bc5a549eea2e2c1ca92d9855320badb96eca94ee9130c6b"

