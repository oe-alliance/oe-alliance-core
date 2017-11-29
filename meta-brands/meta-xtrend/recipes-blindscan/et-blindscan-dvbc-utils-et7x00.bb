SUMMARY = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual/blindscan-dvbc"

PV = "1.6"
PR = "r2"

S = "${WORKDIR}"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "04475b40f25460cead27285f103a0f50"
SRC_URI[sha256sum] = "2e0c285ee8768e8a342ca8ba97f3b1238e3ebc2dcb1b71c4803c532c94804478"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
