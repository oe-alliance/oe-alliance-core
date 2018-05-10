SUMMARY = "halt for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20180509"

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

SRC_URI[md5sum] = "bf963ffa9c10893ea22ed4e5dbb62245"
SRC_URI[sha256sum] = "4df63136e887dc47d299ffe09884112361fccbdca8e9d2ab68aa73c9ff3a9ad5"


