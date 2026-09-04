SECTION = "base"
require conf/license/license-close.inc

PROVIDES += "gb-bluetooth-util"
RPROVIDES:${PN} += "gb-bluetooth-util"

PR = "${SRCDATE}.${SRCDATE_PR}"

SRC_URI = " \
    https://source.mynonpublic.com/gigablue/bluetooth/gb-bluetooth-util-${MACHINE}-${PR}.zip \
    file://99-gigablue-bluetooth.rules \
    file://gb-bluetooth-load-modules \
"

S = "${UNPACKDIR}/gb-bluetooth-util-${MACHINE}"

do_package_qa() {
}

PACKAGE_ARCH := "${MACHINE_ARCH}"
FILES:${PN} = "/"

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/bsa_server ${D}/usr/local/bin/

	install -d ${D}/usr/local/lib
	install -d ${D}/usr/local/lib/modules
	install -m 0644 ${S}/btusb.ko ${D}/usr/local/lib/modules/btusb.ko
	install -m 0644 ${S}/bthid.ko ${D}/usr/local/lib/modules/bthid.ko

	install -d ${D}${sbindir}
	install -m 0755 ${UNPACKDIR}/gb-bluetooth-load-modules ${D}${sbindir}/gb-bluetooth-load-modules

	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${UNPACKDIR}/99-gigablue-bluetooth.rules ${D}${sysconfdir}/udev/rules.d/99-gigablue-bluetooth.rules
}

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "already-stripped"
