KV = "3.4-3.5"
DRIVERDATE = "20151202"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "b6256c79f6266b983430c23683131ba8"
SRC_URI[dm820.sha256sum] = "eec5ea996fedcd2b3a6c997c55df113f7e520cdc515f5c815a572787b1c2350b"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"