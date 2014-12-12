KV = "3.4-3.0"
DRIVERDATE = "20141207"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "a56ab4ab8bd38b9664d8c31090e707be"
SRC_URI[dm7080.sha256sum] = "d6dc1b6123d7b464b1815f72f2fbb38b7cb1eeebd97286ec4cfe6c7d9bbaa5e4"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"