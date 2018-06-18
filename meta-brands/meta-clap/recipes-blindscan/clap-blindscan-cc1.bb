SUMMARY = "blindscan for clap Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "virtual/blindscan-dvbs"

SRCDATE = "20180605"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "http://source.mynonpublic.com/clap/cc1-blindscan-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/clap-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES_${PN}  = "${bindir}/clap-blindscan"

SRC_URI[md5sum] = "14f704f667912157fa9ec55f77ec6262"
SRC_URI[sha256sum] = "60c78481d8acc45e7ff5affbec90c30894d2fd39c4741eff5762ecac8794fbff"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
