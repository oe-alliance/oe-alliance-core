KV = "3.4-3.9"
DRIVERDATE = "20160628"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "7c78ffbc0e7dccaa821e6441ed5aa6fe"
SRC_URI[dm520.sha256sum] = "69d544c1a6571124cb93f31d3c9f5f163c60f9ed87f85c954f66b6e8e738e529"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"