KV = "3.4-3.5"
DRIVERDATE = "20150316"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "b8673d3c49f84249003d5fdb117a2293"
SRC_URI[dm820.sha256sum] = "ed828e36be98a2c4df9e08d82691465dc204f3d44635d6e46c8d676822638afc"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"