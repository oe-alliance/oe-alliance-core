SUMMARY = "Utils for DVB-S blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
PACKAGE_ARCH = "${MACHINE_ARCH}"
require conf/license/license-close.inc
PR = "r1"

PACKAGES = "odin-blindscan-dvbs-utils-${MACHINE} odin-blindscan-dvbc-utils-${MACHINE}"

PROVIDES += "virtual/blindscan-dvbc virtual/blindscan-dvbs"
RPROVIDES_odin-blindscan-dvbc-utils-${MACHINE} += "virtual/blindscan-dvbc"
RPROVIDES_odin-blindscan-dvbs-utils-${MACHINE} += "virtual/blindscan-dvbs"

DOWNLOADMACHINE ?= "${MACHINE}"
SRC_URI = "http://source.mynonpublic.com/download/odin-blindscan-utils-${MACHINE}-${PV}-${SRC}.tar.gz"

S = "${WORKDIR}"

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
    install -m 0755 "${S}/odin_blindscan" "${D}/${bindir}"    
}

FILES_odin-blindscan-dvbc-utils-${MACHINE} = "${bindir}/tda1002x"
FILES_odin-blindscan-dvbs-utils-${MACHINE} = "${bindir}/odin_blindscan"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
