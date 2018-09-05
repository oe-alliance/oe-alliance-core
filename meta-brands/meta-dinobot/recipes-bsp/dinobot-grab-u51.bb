SUMMARY = "grab for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(u51)$"

SRCDATE = "20180905"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES_${PN}  = "aio-grab"
RREPLACES_${PN}  = "aio-grab"
RCONFLICTS_${PN} = "aio-grab"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "45ce72e6fed3c5ba21bd18e9e66b8fa6"
SRC_URI[sha256sum] = "91b1a11e4d5a863a383a6892a50f52ab309a64921f38583cba512abeb855ad4f"
