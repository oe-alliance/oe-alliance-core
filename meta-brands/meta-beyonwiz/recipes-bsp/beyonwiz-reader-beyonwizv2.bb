SUMMARY = "libreader for beyonwiz Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(beyonwizv2)$"

SRCDATE = "20190409"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/beyonwiz/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "497cff3bb466bdcfda5f43e1f9a0f6b8"
SRC_URI[sha256sum] = "ba429ba5e4826694ac9cc9c5fecb5107cbefac42bce14f85d47aa216d203e3aa"
