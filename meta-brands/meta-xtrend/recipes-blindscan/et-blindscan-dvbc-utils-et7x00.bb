SUMMARY = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "https://source.mynonpublic.com/xtrend/${MACHINE}-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual/blindscan-dvbc"
RPROVIDES:${PN} += "virtual-blindscan-dvbc"

PV = "1.6"
PR = "r2"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "9de6f2bbaa412866fcf74d7b9fc1e362"
SRC_URI[sha256sum] = "68a9b616218b740e0aecf7f7f0aa17d2bb917d7bcae53e3ca654812737e34b94"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot

INSANE_SKIP = "32bit-time"
