DESCRIPTION = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "https://source.mynonpublic.com/xtrend/xpeedc-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES:${PN} += "virtual-blindscan-dvbc"

PV = "1.5"
PR = "r2"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "28bcf3cd9de63144d7b0bbf9d5040e74"
SRC_URI[sha256sum] = "05c99af948d6fd1e17765698db5e4be46d1deda868ed0b84f5b03f6af8667200"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time"
