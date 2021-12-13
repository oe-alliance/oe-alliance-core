SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS_${PN} = "ffmpeg libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20211109"

PV = "${SRCDATE}"
PR = "r1"

RPROVIDES_${PN}  = "showiframe"
RREPLACES_${PN}  = "showiframe"
RCONFLICTS_${PN} = "showiframe"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
    if [ -e ${S}/dinobotplayer ]; then
        install -m 0755 ${S}/dinobotplayer ${D}/${bindir}
    fi
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/showiframe ${bindir}/dinobotplayer"

SRC_URI[md5sum] = "1c17f1e5386ff47dbf6703185660ea08"
SRC_URI[sha256sum] = "ed1eff767b6b4e6eda3fb3ccf08e21b9ce257676800153989c7a2b39f0c50702"
