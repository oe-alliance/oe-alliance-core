KV = "3.4-3.5"
DRIVERDATE = "20150901"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "f1a930b20713323185964d09bc91ef61"
SRC_URI[dm7080.sha256sum] = "55879874b0e6beed9168179cc1d2a2b37b1d38036b86f3f83c0fdf7d447007ab"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"