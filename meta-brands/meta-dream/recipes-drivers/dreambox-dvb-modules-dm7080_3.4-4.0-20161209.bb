KV = "3.4-4.0"
DRIVERDATE = "20161209"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "2535e03a26ec19258d6d410682a0a03a"
SRC_URI[dm7080.sha256sum] = "cc7baf8109f9687988f5759f84cdfb9dea56fe8694bb9a7a0229e3595f2efd1a"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"