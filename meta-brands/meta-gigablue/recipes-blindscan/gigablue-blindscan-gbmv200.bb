SUMMARY = "blindscan for Gigablue Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:${PN} += "virtual-blindscan-dvbs"

SRCDATE = "20190307"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "https://source.mynonpublic.com/gigablue/mv200/${MACHINE}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gigablue_blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/gigablue_blindscan"

SRC_URI[md5sum] = "b71bd793f450cd5e492e9171575475a4"
SRC_URI[sha256sum] = "bb55956f8b7b32bb483040151f374cd84162cdff6f12f3943504e432c3f16311"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
