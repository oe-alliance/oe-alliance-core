KV = "3.4-3.5"
DRIVERDATE = "20151104"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "973e62b4a9e4f22341c985d6955306d3"
SRC_URI[dm820.sha256sum] = "9adeb6d45e0730f29757d7d5cdf774b456cb841a689fe5137949851ed93a6f5a"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"