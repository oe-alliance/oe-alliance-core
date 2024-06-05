SUMMARY = "blindscan for Uclan Model ${SOC_FAMILY}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:${PN} += "virtual-blindscan-dvbs"

SRCDATE = "20190305"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "https://source.mynonpublic.com/uclan/${SOC_FAMILY}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/uclan-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/uclan-blindscan"

SRC_URI[md5sum] = "ee98e5c30d36a7afaff364e6015e0956"
SRC_URI[sha256sum] = "2eb583117f45a5e7d88a2843ff60ba7cf2420deb1987af83ae6299b8433a21ba"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
