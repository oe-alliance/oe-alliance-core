SUMMARY = "libreader for Qviart Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(og2ott4k)$"

SRCDATE = "20220211"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/qviart/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "720569028ae20c15f04992b5a6ae0e10"
SRC_URI[sha256sum] = "5358a0886648dee69ad51c5a4471c2beb43e07f3053bbfa96ceab4f05a13435b"

INSANE_SKIP:${PN} += "already-stripped"
