SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u51)$"

SRCDATE = "20181130"

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

SRC_URI[md5sum] = "c72c4a848a7fd21e6c01b155179d6df0"
SRC_URI[sha256sum] = "697019cafea41c041af7699a4ad75c31e480ed6c60c1806b395f4925d09047df"
