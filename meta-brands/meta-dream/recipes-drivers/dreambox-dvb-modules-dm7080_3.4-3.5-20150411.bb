KV = "3.4-3.5"
DRIVERDATE = "20150316"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "b4f5268d03abe7564128dae92f92a207"
SRC_URI[dm7080.sha256sum] = "affc941cad8f00723106a777cabfa248f993111245fd08a5b2a5a94e7bf89c46"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"