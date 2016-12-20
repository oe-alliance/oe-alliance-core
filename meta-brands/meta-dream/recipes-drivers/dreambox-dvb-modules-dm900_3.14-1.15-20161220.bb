KV = "3.14-1.15"
DRIVERDATE = "20161220"

require dreambox-dvb-modules-dm900.inc

SRC_URI[dm900.md5sum] = "4389c3d21de5f0a7eb3ebae06ae8c4cb"
SRC_URI[dm900.sha256sum] = "c333de9f52057dca60fc5d0a7e1ac0e3a68c2ed92c1c72f272b10c00699d5330"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm900)$"
