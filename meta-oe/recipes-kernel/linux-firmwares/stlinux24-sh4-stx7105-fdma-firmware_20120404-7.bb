STLINUX_FW_FILE_NAME = "stlinux24-sh4-fdma-firmware-${PV}.noarch.rpm"
DESCRIPTION = "Firmware for the STx7111 CPU Flexible DMA engine (FDMA)"

require stlinux24-sh4-fw.inc 

SRC_URI[md5sum] = "9d3feac1d878de02858c85fdf3ea0ba7"
SRC_URI[sha256sum] = "bb373f570585573b10a6c46eb09fd8b92bca72e0c4aa8148af90331e939651b8"

PR = "${INC_PR}.0"

do_install() {
	install -d ${D}${base_libdir}/firmware
	install -m 0644 ${S}/lib/firmware/fdma_STx7105_* ${D}${base_libdir}/firmware
}
