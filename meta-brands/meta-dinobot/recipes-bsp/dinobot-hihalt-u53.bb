SUMMARY = "halt for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20180427"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/dinobot/${MACHINE}-hihalt-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
    install -m 0755 ${S}/timerTask.py ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt"
FILES_${PN} += "${bindir}/timerTask.py"

SRC_URI[md5sum] = "4dc054815e8ceefcecca587175e00684"
SRC_URI[sha256sum] = "6a3d60ef68e969d8469887b454ad5869333258a6411729133a4ce44421d04226"



