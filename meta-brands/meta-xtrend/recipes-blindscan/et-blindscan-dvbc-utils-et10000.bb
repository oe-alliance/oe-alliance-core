SUMMARY = "Utils for DVB-C blindscan"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "http://www.et-view.com/img_up/shop_pds/bh190/Img_Xtrend/${MACHINE}-dvbc-blindscan-${PV}.zip"

PROVIDES += "virtual+blindscan-dvbc"
RPROVIDES_${PN} += "virtual+blindscan-dvbc"

PV = "1.2"
PR = "r1"

S = "${WORKDIR}"

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${S}/tda1002x" "${D}/${bindir}"
}

SRC_URI[md5sum] = "459be6d6e8336d182de1ada4a8dfd3c0"
SRC_URI[sha256sum] = "8f67c7f79d8a4b9d7eea5a1574d1945e8c7c6eea5560cadb0566c4533e01bb12"

do_prepare_recipe_sysroot[noexec] = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
do_compile[noexec] = "1"
deltask do_populate_sysroot
