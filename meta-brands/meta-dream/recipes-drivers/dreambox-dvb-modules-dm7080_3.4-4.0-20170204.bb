KV = "3.4-4.0"
DRIVERDATE = "20170204"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "772af2c30dec559ad436debe870b7a17"
SRC_URI[dm7080.sha256sum] = "97a9ffa98604f518023776c6708e418b377a971359def7e5c7a9535d45200f25"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"