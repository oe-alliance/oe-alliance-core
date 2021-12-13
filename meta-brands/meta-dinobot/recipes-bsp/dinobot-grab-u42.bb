SUMMARY = "grab for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(u42)$"

SRCDATE = "20211109"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "aio-grab"
RREPLACES:${PN}  = "aio-grab"
RCONFLICTS:${PN} = "aio-grab"

SRC_URI = "http://source.mynonpublic.com/dinobot/${MACHINE}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "1ba97ed83e91c307e8fa0fff1de242f5"
SRC_URI[sha256sum] = "6286b744470d84bc29b8fb5822a420419ced6d1b0622857d7d4517c6d1cc61f1"
