SUMMARY = "libreader for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(sfx6008)$"

SRCDATE = "20220408"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "http://source.mynonpublic.com/octagon/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "77b4eb76bfb1f6846319c12f7df97685"
SRC_URI[sha256sum] = "c7a4f2c6fa56326d661994f4f01e383a0ca5686f1414ee28e9f2fd9c1827e88a"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
