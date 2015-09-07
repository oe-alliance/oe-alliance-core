KV = "3.4-3.5"
DRIVERDATE = "20150902"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "0b62dc6ac629afae21ee48439f83064d"
SRC_URI[dm7080.sha256sum] = "9bfa8be1a86fc4daa6b98919f238f05517ec4704d300091dae88ce4853ce9ba8"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"