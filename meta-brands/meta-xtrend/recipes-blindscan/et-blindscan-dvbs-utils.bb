SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://avl_xtrend_blindscan"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES_${PN} += "blindscan-dvbs"

PV = "1.1"
PR = "r5"

S = "${WORKDIR}"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${WORKDIR}/avl_xtrend_blindscan" "${D}/${bindir}"
}

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
