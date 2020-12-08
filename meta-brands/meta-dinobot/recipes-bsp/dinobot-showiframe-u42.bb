SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "ffmpeg libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20201126"

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
    install -m 0755 ${S}/dinobotplayer ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/showiframe ${bindir}/dinobotplayer"

SRC_URI[md5sum] = "921b6f5af49fc2f9e51d7421aa3f9fec"
SRC_URI[sha256sum] = "d74d041436e7c281b5bda8977bff9b6111c4d3e24acadcace337531f76b95a15"
