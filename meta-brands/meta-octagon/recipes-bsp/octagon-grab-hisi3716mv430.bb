SUMMARY = "grab for Octagon Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(sfx6008)$"

SRCDATE = "20221013"

PV = "${SRCDATE}"
PR = "r0"

RPROVIDES_${PN}  = "aio-grab"
RREPLACES_${PN}  = "aio-grab"
RCONFLICTS_${PN} = "aio-grab"

SRC_URI = "https://source.mynonpublic.com/octagon/${SOC_FAMILY}-grab-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/grab ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/grab"

SRC_URI[md5sum] = "5adc993b1a9e7bfaacb9fc0115c806a1"
SRC_URI[sha256sum] = "8e42b489f6fff36fd19896d2eb7903a6abde904e000f97ce4c5e132c1d66f98c"
