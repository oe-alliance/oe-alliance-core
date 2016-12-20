KV = "3.4-4.0"
DRIVERDATE = "20161220"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "db8294723d0820047b68c2a6fbd580bd"
SRC_URI[dm520.sha256sum] = "ddc14bfbed4303547de3f28d28c543f51eef8875359e35ffcfafe88200654af9"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"