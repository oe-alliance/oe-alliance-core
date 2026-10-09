SUMMARY = "Broadcom BCM4356 Bluetooth firmware for the internal UART controller"
require conf/license/license-close.inc

inherit allarch

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

SRCREV = "c44d5848cff873686968151a6b83156282f6a864"
SRC_URI = "git://github.com/LibreELEC/brcmfmac_sdio-firmware.git;protocol=https;branch=master"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware/brcm ${D}${sysconfdir}/firmware
	install -m 0644 ${S}/BCM4356A2.hcd ${D}${nonarch_base_libdir}/firmware/brcm/
	# hciattach bcm43xx looks up /etc/firmware/<chip name>.hcd, the chip reports BCM4354A2
	ln -sf ${nonarch_base_libdir}/firmware/brcm/BCM4356A2.hcd ${D}${sysconfdir}/firmware/BCM4354A2.hcd
}

FILES:${PN} = "${nonarch_base_libdir}/firmware/brcm ${sysconfdir}/firmware"
