SUMMARY = "libreader for clap Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(cc1)$"

SRCDATE = "20180910"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/clap/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "b14aab849986188dc6fef6652f8e4357"
SRC_URI[sha256sum] = "ef740732fb254f6d08b30adc5f3393dfc3fb5824e5265e54cfee9130e20e2501"
