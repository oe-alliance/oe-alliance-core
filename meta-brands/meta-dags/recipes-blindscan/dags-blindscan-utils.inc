DESCRIPTION = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"

PV = "1.0"
PR = "r0"

SRC_URI = "http://en3homeftp.net/pub/down/dags_blindscan_${SOC_FAMILY}.tar.gz"

S = "${WORKDIR}"

PROVIDES += "virtual/blindscan-dvbs"
RPROVIDES:dags-blindscan-utils-hisi3798mv200 += "virtual-blindscan-dvbs"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
