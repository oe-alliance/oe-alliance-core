SUMMARY = "libreader for Anadol Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(ip8)$"

SRCDATE = "20221216"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/anadol/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "27d7cd13def278cae11223715aa8dcd8"
SRC_URI[sha256sum] = "0c49156073a9075611a0be5ed23d29a0c3d154d694c68ae3336cc0f7fdf23d99"

INSANE_SKIP:${PN} += "already-stripped"
