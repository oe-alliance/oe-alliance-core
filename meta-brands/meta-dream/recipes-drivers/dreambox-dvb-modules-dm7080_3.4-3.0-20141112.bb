KV = "3.4-3.0"
DRIVERDATE = "20141112"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "1671a36fe6ed4e9e7b0c86b69495bc03"
SRC_URI[dm7080.sha256sum] = "17919bd197f0ca155580b51f61d561d555c058b9d2ae825bb2224957c4959eff"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"