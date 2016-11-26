KV = "3.4-3.5"
DRIVERDATE = "20161124"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "e9e6d66f5867ac645d03326e1c359656"
SRC_URI[dm820.sha256sum] = "dfbcb9b2cb5b088a38e1b0b2c57f40614d7d90f95cb917efa8cdfc643346ff73"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"