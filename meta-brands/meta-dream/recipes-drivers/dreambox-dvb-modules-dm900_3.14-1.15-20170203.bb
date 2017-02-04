KV = "3.14-1.15"
DRIVERDATE = "20170203"

require dreambox-dvb-modules-dm900.inc

SRC_URI[dm900.md5sum] = "4e5cce2d35389c46bfb01ce6a7454a1d"
SRC_URI[dm900.sha256sum] = "b8a1b34c15fcb3f80fe22777bbcbc0fef8ee1950346f6315a12a1e0dd23d9f5f"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm900)$"
