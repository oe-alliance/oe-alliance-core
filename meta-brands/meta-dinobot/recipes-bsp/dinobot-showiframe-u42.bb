SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "ffmpeg libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20201217"

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

SRC_URI[md5sum] = "468b4ef6946fe083e6bea5b6303fa6c6"
SRC_URI[sha256sum] = "3e65e55fd41ffbe6c266cfc0daaedadc3f4b6d70f73f756f7ff2c1feccf85b34"
