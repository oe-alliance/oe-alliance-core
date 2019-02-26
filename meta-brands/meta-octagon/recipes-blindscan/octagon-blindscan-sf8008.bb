SUMMARY = "blindscan for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

SRCDATE = "20190226"

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

SRC_URI[md5sum] = "022b4899e397c1c312e97721836b215f"
SRC_URI[sha256sum] = "375a0c9c8a1aab230c9ed30c5c49e023229aee91ca446621bdaab825ab25c514"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
