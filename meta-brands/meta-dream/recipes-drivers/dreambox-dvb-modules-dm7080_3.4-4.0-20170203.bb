KV = "3.4-4.0"
DRIVERDATE = "20170203"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "33828f1d7372aa99d9455684d5e699b5"
SRC_URI[dm7080.sha256sum] = "246963ab02a1a9c639c201c7649286675d139461a0927dbf947c01bed0ed66fd"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"