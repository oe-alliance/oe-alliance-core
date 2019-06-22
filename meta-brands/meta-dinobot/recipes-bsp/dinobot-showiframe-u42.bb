SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20190620"

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

SRC_URI[md5sum] = "c83a83d1280907e205ec5e374fa0bb52"
SRC_URI[sha256sum] = "0076533c2905fe89d854250f6314dfe0e667cb4596c9dcbdbd03535dd92123ae"
