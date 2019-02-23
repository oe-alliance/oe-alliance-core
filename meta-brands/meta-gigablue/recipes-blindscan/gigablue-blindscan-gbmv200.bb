SUMMARY = "blindscan for Gigablue Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

SRCDATE = "20190216"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/gigablue/mv200/${MACHINE}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gigablue_blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/gigablue_blindscan"

SRC_URI[md5sum] = "3f1c5f7d5b0b5a418a016687ea5994c8"
SRC_URI[sha256sum] = "c78916982ee95a6152e319b5f8f99f02d55be54f51ad1fa8b5a1179e9f0bdd77"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
