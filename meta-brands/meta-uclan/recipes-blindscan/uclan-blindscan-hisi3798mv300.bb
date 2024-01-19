SUMMARY = "blindscan for Uclan Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:${PN} += "virtual-blindscan-dvbs"

SRCDATE = "20230217"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "https://source.mynonpublic.com/uclan/${SOC_FAMILY}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/uclan-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/uclan-blindscan"

SRC_URI[md5sum] = "f02887e83991ea61ca63e4eb6ae1787a"
SRC_URI[sha256sum] = "c628e088e0547d8b81b39347df3c5b3347110aaba69242fb444b79e12953f7c8"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
