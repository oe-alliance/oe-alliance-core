SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS:${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u43)$"

SRCDATE = "20211109"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "showiframe"
RREPLACES:${PN}  = "showiframe"
RCONFLICTS:${PN} = "showiframe"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/showiframe"

SRC_URI[md5sum] = "2e027a44a1d8440f614c9ec22eb22e6d"
SRC_URI[sha256sum] = "466f35bbf855000a58e100c30b95c4df414ca3f0ee6ff8a1bc92cb324981cea0"
