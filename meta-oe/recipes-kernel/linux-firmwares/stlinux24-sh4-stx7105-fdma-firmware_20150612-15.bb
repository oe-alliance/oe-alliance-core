STLINUX_FW_FILE_NAME = "stlinux24-sh4-fdma-firmware-${PV}.noarch.rpm"
DESCRIPTION = "Firmware for the STx7111 CPU Flexible DMA engine (FDMA)"

require stlinux24-sh4-fw.inc 

SRC_URI[md5sum] = "e425110da8ea5c80e4d1c2cc398ae9bf"
SRC_URI[sha256sum] = "d94521bfceeb132a1e2eed898c457b678c9ef6c08f0922b2bdf67861ad301f63"

PR = "${INC_PR}.0"

do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware
	install -m 0644 ${S}/lib/firmware/fdma_STx7105_* ${D}${nonarch_base_libdir}/firmware
}
