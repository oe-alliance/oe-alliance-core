SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u53)$"

SRCDATE = "20190715"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES_${PN}  = "showiframe"
RREPLACES_${PN}  = "showiframe"
RCONFLICTS_${PN} = "showiframe"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/showiframe"

SRC_URI[md5sum] = "6c466420bc548163c52ced73777a8eae"
SRC_URI[sha256sum] = "4b4a52f25d41eaaa450c943bf4f6cad86e92ae38ddd8c800c353f29f3af768d3"
