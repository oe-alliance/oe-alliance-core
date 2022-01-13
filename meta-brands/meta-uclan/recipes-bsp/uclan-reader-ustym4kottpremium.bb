SUMMARY = "libreader for Uclan Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(ustym4kottpremium)$"

SRCDATE = "20220113"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/uclan/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INSANE_SKIP:${PN} += "already-stripped"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "df259ada24913f73995598a4f1d90c0b"
SRC_URI[sha256sum] = "62fc68df22e68475b79c8290a318484e9afeae0fd26c7aa7c36bf205f736c18e"
