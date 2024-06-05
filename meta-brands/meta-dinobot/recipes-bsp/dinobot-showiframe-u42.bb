SUMMARY = "showiframe for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
RDEPENDS:${PN} = "ffmpeg libjpeg-turbo"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20210607"

PV = "${SRCDATE}"
PR = "r1"

RPROVIDES:${PN}  = "showiframe"
RREPLACES:${PN}  = "showiframe"
RCONFLICTS:${PN} = "showiframe"

SRC_URI = "https://source.mynonpublic.com/dinobot/${MACHINE}-showiframe-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/showiframe ${D}/${bindir}
    if [ -e ${S}/dinobotplayer ]; then
        install -m 0755 ${S}/dinobotplayer ${D}/${bindir}
    fi
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/showiframe ${bindir}/dinobotplayer"

SRC_URI[md5sum] = "17f8ba5c12ea0a9b48bfa2391d5a99a4"
SRC_URI[sha256sum] = "13bef3e35533a7781689d7568479056a51c560746dbd96538d1efdeac237ff96"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
