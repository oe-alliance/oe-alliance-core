KV = "3.4-3.5"
DRIVERDATE = "20150902"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "1c9b04d0f49718d6968d94688ad4da55"
SRC_URI[dm820.sha256sum] = "4f3e913752f29201e405ee20bc1be0851de0293801c5c68438930f5ba365190e"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"