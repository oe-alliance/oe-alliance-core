SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PROVIDES += "gb-bluetooth-util"
RPROVIDES:${PN} += "gb-bluetooth-util"

PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    https://source.mynonpublic.com/gigablue/bluetooth/gb-bluetooth-util-${MACHINE}-${PR}.tar.gz \
"

S="${WORKDIR}/gb-bluetooth-util-${MACHINE}"

do_package_qa() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES:${PN}="/"

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${UNPACKDIR}/bsa_server ${D}/usr/local/bin/

	install -d ${D}/usr/local/lib
	install -d ${D}/usr/local/lib/modules
	install -m 0644 ${UNPACKDIR}/btusb.ko ${D}/usr/local/lib/modules/btusb.ko
	install -m 0644 ${UNPACKDIR}/bthid.ko ${D}/usr/local/lib/modules/bthid.ko
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped"
