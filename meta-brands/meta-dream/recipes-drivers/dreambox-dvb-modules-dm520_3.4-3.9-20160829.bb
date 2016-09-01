KV = "3.4-3.9"
DRIVERDATE = "20160829"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "aa053da9676fa346334878a42d939145"
SRC_URI[dm520.sha256sum] = "7a65254fb631b9d1db0094a85470036f435ce57c0740d3ff0127d7ddd34317fd"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"