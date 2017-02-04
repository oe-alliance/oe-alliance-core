KV = "3.4-4.0"
DRIVERDATE = "20170203"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "6f2d8c960753e5d2c81018bece779d54"
SRC_URI[dm520.sha256sum] = "a26de84654c6e422359ad4d4cd6690e2521059b45f53b73c9d65dec18c7ca4b4"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"