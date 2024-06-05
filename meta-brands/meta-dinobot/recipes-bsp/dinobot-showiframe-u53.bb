SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS:${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u53)$"

SRCDATE = "20200710"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "showiframe"
RREPLACES:${PN}  = "showiframe"
RCONFLICTS:${PN} = "showiframe"

SRC_URI = "https://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/showiframe"

SRC_URI[md5sum] = "e86b968fd7c4522d36ef567acf37c932"
SRC_URI[sha256sum] = "9ab1f2acabd8ae179a679cdf48880c43233d5e7ff9fc0593f43e59842c39c403"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
