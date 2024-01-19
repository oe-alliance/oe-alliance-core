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
PR = "r1"

S = "${WORKDIR}"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "0ff7ec890b2397fc7ae333de0d33e81b"
SRC_URI[sha256sum] = "e6dc2af7fd4c07fd7a6e0aa1730fd20e21793c1f6f4dd6554f75eda5436ac850"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
