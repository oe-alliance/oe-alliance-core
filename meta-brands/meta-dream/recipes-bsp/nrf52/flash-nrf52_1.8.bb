SUMMARY = "Flasher for NRF52 based BLE Transceiver"

SRC_URI[aarch64.md5sum] = "01e790af99887020fbcd618341e60671"
SRC_URI[aarch64.sha256sum] = "f4c6692dbe6feebc747860f79931235b67604a840b3d7e0052b8080f80770e40"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

inherit opendreambox-precompiled-binary4

PR = "r1"

SRC_URI += "file://60-flash-nrf52.rules"

RDEPENDS:${PN} = "libcrypto10"

INSANE_SKIP:${PN} += "file-rdeps ldflags"

do_install:append() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${UNPACKDIR}/60-flash-nrf52.rules ${D}${sysconfdir}/udev/rules.d/
}

pkg_postinst:${PN}() {
if [ -z "$D" ]; then
	udevadm trigger --action=add --sysname-match=mmcblk0boot0
	udevadm settle
fi
}
