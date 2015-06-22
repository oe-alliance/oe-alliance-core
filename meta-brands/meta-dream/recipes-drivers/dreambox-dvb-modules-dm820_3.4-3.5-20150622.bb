KV = "3.4-3.5"
DRIVERDATE = "20150622"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "7075578e58e535d54e9d7bff27baa2b3"
SRC_URI[dm820.sha256sum] = "b7872ed7775d93e170143a3e9b559ce9445fcfd6a57621241d33fc1fb1e2ad6f"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"