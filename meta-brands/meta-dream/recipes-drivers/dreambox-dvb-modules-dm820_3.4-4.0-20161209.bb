KV = "3.4-4.0"
DRIVERDATE = "20161209"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "2ba081a96cd33c1f3487a71a475d4ee6"
SRC_URI[dm820.sha256sum] = "c1ab8a05fcd526576a2c2754932376a5aab1bd83c5d2c2688cf9c0411dc20a87"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"