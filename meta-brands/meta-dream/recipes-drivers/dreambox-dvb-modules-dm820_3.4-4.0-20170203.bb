KV = "3.4-4.0"
DRIVERDATE = "20170203"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "7385d7822323c98f08d740ac89cbeff4"
SRC_URI[dm820.sha256sum] = "82ffac6cf404061c6378345114b360ca5b615a7b759bdfc87861a55bbcf730b6"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"