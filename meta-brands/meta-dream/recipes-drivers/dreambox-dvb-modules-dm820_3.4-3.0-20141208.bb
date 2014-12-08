KV = "3.4-3.0"
DRIVERDATE = "20141208"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "553041f73d18e3c327a48718b9d6464f"
SRC_URI[dm820.sha256sum] = "eb5a31fb9be0c5cf4a4fb664de660aa7fbbb5f59be14333008858eb82f3954de"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"