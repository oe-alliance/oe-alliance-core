SUMMARY = "libreader for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(sfx6008)$"

SRCDATE = "20221216"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "2a8cc59292211df41b508133b5297f0d"
SRC_URI[sha256sum] = "296f6626e32e0ad3bf73bf15bec38ea1ea75023124ed66846d81cde7083b5fd5"

INSANE_SKIP_${PN} += "already-stripped"
