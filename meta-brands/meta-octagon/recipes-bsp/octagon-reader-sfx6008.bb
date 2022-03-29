SUMMARY = "libreader for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(sfx6008)$"

SRCDATE = "20220328"

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

FILES_${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "75d68a064d90f23b2b38fdc7e6ab47ef"
SRC_URI[sha256sum] = "d6ecf852bb9777f775d9b0fd7eddadc7ad4c710d0020099a1c69e22f4a801acb"

INSANE_SKIP_${PN} += "already-stripped"
