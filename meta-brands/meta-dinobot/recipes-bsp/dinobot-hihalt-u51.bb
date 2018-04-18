SUMMARY = "halt for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20180418"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/dinobot/${MACHINE}-hihalt-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
    install -m 0755 ${S}/hipmoc ${D}/${bindir}
    install -m 0755 ${S}/timerTask.py ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt"
FILES_${PN} += "${bindir}/hipmoc"
FILES_${PN} += "${bindir}/timerTask.py"

SRC_URI[md5sum] = "8cd0b3d01533111c99be1b5e7fe87eff"
SRC_URI[sha256sum] = "657b950a3825002d53e40574c64fcf7ef922b129c425f29e0d06bea703c02592"


