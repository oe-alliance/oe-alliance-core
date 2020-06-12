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

SRC_URI[md5sum] = "189c3f60dacd1848bf8622ab0a289d94"
SRC_URI[sha256sum] = "961f2fae8ca4ff33c3c0308992b6b9c0f75e89e6fd47a1cba631f255b3b50874"

