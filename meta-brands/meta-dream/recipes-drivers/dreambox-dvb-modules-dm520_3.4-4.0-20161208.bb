KV = "3.4-4.0"
DRIVERDATE = "20161208"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "feed542fdf6d2e563e6e2a23902a0b7a"
SRC_URI[dm520.sha256sum] = "442101c0b139bc48e722dc7e6a35c7a5cfa0e59782f5379e3719a728e2a68140"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"