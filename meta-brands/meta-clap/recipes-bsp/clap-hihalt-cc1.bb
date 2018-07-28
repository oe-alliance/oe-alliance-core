SUMMARY = "halt for clap Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS_${PN} = "harfbuzz"

SRCDATE = "20180728"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/clap/${MACHINE}-hihalt-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt"

SRC_URI[md5sum] = "d1f40acc4f2fe1cc903b230434492ffa"
SRC_URI[sha256sum] = "2d207035997ab422e699d2db9a1c25220a6c12cdc278222b0af4762b2e116cb2"


