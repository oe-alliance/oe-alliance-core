KV = "3.4-3.9"
DRIVERDATE = "20160628"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "dc4f14c276d0a1b77e6aab394e296bd6"
SRC_URI[dm520.sha256sum] = "d98784e29aa5f4fa15436ac84ba2b99f365a63241c695cd71e4abd71a9a44540"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"