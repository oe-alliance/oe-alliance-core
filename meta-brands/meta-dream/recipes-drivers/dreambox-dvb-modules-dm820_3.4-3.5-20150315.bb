KV = "3.4-3.5"
DRIVERDATE = "20150315"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "372657cac34d98df3581407c93b87074"
SRC_URI[dm820.sha256sum] = "08caffa17667c0c340465b93265d888d17bccb673ad01d3b32fef663f6512852"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"