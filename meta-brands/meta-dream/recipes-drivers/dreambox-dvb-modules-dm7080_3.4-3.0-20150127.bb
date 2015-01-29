KV = "3.4-3.0"
DRIVERDATE = "20150127"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "b2f38264806222ff33aaefc9c63d8888"
SRC_URI[dm7080.sha256sum] = "34c3e0cab71545925341704404cae3b2a9046b3be0251cae2b596dcf8e2a8c67"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"