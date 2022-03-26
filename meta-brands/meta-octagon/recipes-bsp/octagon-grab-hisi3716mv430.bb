SUMMARY = "grab for Octagon Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(sfx6008)$"

SRCDATE = "20220321"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "aio-grab"
RREPLACES:${PN}  = "aio-grab"
RCONFLICTS:${PN} = "aio-grab"

SRC_URI = "http://source.mynonpublic.com/octagon/${SOC_FAMILY}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "d5899d34968ffe870b11b9f2d8aa9140"
SRC_URI[sha256sum] = "2eef114b36decde8c228a33dd693cc0717fbee5994c87c9cf33c1daed4777752"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

