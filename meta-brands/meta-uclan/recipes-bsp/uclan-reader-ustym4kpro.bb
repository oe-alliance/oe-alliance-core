SUMMARY = "libreader for uclan Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(ustym4kpro)$"

SRCDATE = "20221220"

PV = "${SRCDATE}"
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/uclan/${MACHINE}-libreader-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

INSANE_SKIP:${PN} += "already-stripped"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "9f500f357e10eb3191f4c9f9736c2a50"
SRC_URI[sha256sum] = "1cd12c99cd87366d6261f6647cc0e27f97aa31b3e4c52f9a354dd24145e7771c"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
