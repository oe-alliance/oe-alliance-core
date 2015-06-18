KV = "3.4-3.5"
DRIVERDATE = "20150618"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "cc30d5f2ca463088bacdc2d3af4d2f18"
SRC_URI[dm7080.sha256sum] = "a7f07a710ec844c72b329855e5e0d7f668bc9ba77b37f98186d82c47ff35bb98"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"