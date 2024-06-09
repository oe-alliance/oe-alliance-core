SECTION = "base"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PROVIDES += "vuplus-bluetooth-util"
RPROVIDES:${PN} += "vuplus-bluetooth-util"

PR="${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    https://source.mynonpublic.com/vuplus/release/bt/vuplus-bluetooth-util-${MACHINE}-${PR}.tar.gz \
"

S = "${WORKDIR}/sources-unpack"
UNPACKDIR = "${S}"

do_package_qa() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES:${PN}="/"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${UNPACKDIR}/vuplus-bluetooth-util-${MACHINE}/bsa_server ${D}${bindir}

	install -d ${D}/usr/local/modules
	install -m 0644 ${UNPACKDIR}/vuplus-bluetooth-util-${MACHINE}/btusb.ko ${D}/usr/local/modules/btusb.ko
	install -m 0644 ${UNPACKDIR}/vuplus-bluetooth-util-${MACHINE}/bthid.ko ${D}/usr/local/modules/bthid.ko

	if [ -n "${FIRMWARE_FILE}" ]; then
		install -d ${D}/lib/firmware/${FIRMWARE_PATH}
		install -m 0644 ${UNPACKDIR}/vuplus-bluetooth-util-${MACHINE}/${FIRMWARE_FILE} ${D}/lib/firmware/${FIRMWARE_PATH}
	fi
}
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped"
