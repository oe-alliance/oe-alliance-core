SUMMARY = "halt for clap Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20180411"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/clap/cc1-hihalt-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/hihalt ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/hihalt"

SRC_URI[md5sum] = "c3abde0156569494d2e020b62915aa6f"
SRC_URI[sha256sum] = "45321b64f5808e9a05232bf122063e89a0fdf7119843e5037324691f5b68a1bd"


