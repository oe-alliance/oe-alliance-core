KV = "3.4-3.5"
DRIVERDATE = "20150316"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "84e98510d85f63b4056f826d30756204"
SRC_URI[dm7080.sha256sum] = "8f873a74e01aaff9ea30351a1465a87f48f2cc20460a6aa549dfcebabc557fac"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"