KV = "3.4-3.5"
DRIVERDATE = "20150316"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "0587d7a4eb4e20c38e64140a80e6aada"
SRC_URI[dm7080.sha256sum] = "4fc4a97db19df2387b14519558071f696259021c661ef6d75ef49b4f58ebd5c3"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"