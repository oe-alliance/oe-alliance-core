SUMMARY = "grab for dinobot Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(u55)$"

SRCDATE = "20200828"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "aio-grab"
RREPLACES:${PN}  = "aio-grab"
RCONFLICTS:${PN} = "aio-grab"

SRC_URI = "https://source.mynonpublic.com/dinobot/${MACHINE}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "5ab7009875ee85114dc1a73e6bcc3e72"
SRC_URI[sha256sum] = "4c2b23220313cb4e6848ad477e6f72b4574780bbdb540a429c3e449fe44118e4"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
