SUMMARY = "blindscan for AMIKO Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:${PN} += "virtual-blindscan-dvbs"

SRCDATE = "20190306"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI  = "https://source.mynonpublic.com/amiko/${MACHINE}-blindscan-${SRCDATE}.zip"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/amiko-blindscan ${D}/${bindir}
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/amiko-blindscan"

SRC_URI[md5sum] = "c1f286a12791e0e5461f73ee7aae0712"
SRC_URI[sha256sum] = "2da5f797ee4147a0f9f35c9c72dfa958258deb492a074c7cf60a7a3bf467dea9"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
