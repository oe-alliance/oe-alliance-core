SUMMARY = "blindscan for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

SRCDATE = "20190305"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/octagon/${MACHINE}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/octagon-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/octagon-blindscan"

SRC_URI[md5sum] = "643ec0736a15f3aa16023772d1462a13"
SRC_URI[sha256sum] = "c1c72d745d094c6b78203302e187e828e2293b72c804030c9246c296f63d4aa1"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
