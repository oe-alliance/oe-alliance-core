SUMMARY = "blindscan for qviart Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:${PN} += "virtual-blindscan-dvbs"

SRCDATE = "20230710"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "https://source.mynonpublic.com/qviart/${SOC_FAMILY}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/qviart-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/qviart-blindscan"

SRC_URI[md5sum] = "e89dba8e3d3d905953ae5d4569209174"
SRC_URI[sha256sum] = "bec142c10130723ef034768e39d130b94d00ae10c6a5c0f787de79997ec4d53f"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
