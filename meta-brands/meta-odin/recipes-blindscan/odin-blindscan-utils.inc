SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc
PR = "r2"

SRC_URI = "https://source.mynonpublic.com/download/odin-blindscan-utils-${MACHINE}-${PV}-${SRC}.tar.gz"

S = "${UNPACKDIR}"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/odin_blindscan" "${D}/${bindir}"    
}

FILES:odin-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES:odin-blindscan-dvbs-utils-${MACHINE} = "${bindir}/odin_blindscan"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time"
