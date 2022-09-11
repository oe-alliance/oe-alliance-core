SUMMARY = "libreader for Gigablue Model ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINEBUILD}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(gbmv200)$"

SRCDATE = "20200612"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-libreader-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "979968a04bc0a04b856dfa13c6735bd7"
SRC_URI[sha256sum] = "22711ee3d0e0956665becee8527dc2ea2ac7728ab909feded518ae38faa76bf4"

