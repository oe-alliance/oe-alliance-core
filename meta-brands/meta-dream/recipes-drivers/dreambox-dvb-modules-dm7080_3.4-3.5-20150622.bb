KV = "3.4-3.5"
DRIVERDATE = "20150622"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "813fb6f1364068280c41387788e40570"
SRC_URI[dm7080.sha256sum] = "d0f6ee844a4b4a8efa6c2e63f7e47d66fc09ad02dd0b690b5454f9b0c651e083"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"