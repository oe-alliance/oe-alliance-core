DESCRIPTION = "Utils for blindscan"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH := "${MACHINE_ARCH}"

PV = "1.0"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/dags/dags_blindscan_${SOC_FAMILY}.tar.gz"

S = "${UNPACKDIR}"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time"
