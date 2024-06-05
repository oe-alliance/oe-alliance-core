SUMMARY = "grab for dags Model ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(dagsmv200)$"

SRCDATE = "20211031"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES:${PN}  = "aio-grab"
RREPLACES:${PN}  = "aio-grab"
RCONFLICTS:${PN} = "aio-grab"

SRC_URI = "http://en3homeftp.net/pub/down/${MACHINEBUILD}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
FILES:${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "8adc85fe14b79f296fc7f0f1376e193e"
SRC_URI[sha256sum] = "6979e6f2729a6c6748c48bf0eb7cf9ce35a7122a480981b072e9e7c9c3e297ef"
