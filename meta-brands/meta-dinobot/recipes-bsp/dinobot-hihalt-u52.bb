SUMMARY = "halt for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20180425"

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

SRC_URI[md5sum] = "005dfde39727476ba00c6aca0c874c52"
SRC_URI[sha256sum] = "831297fbe9cece5f291e091eac573b92183e0a99ceaf2644a4eca5a97fa49ce5"


