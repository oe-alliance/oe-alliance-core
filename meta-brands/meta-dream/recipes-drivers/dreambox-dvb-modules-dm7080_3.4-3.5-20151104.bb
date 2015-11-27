KV = "3.4-3.5"
DRIVERDATE = "20151104"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "e197affedaf57365c1ef80f72fef3a9c"
SRC_URI[dm7080.sha256sum] = "2f322506000f27688b32ffc34b0328ac7d8e40340946629c9836d89e5e93e80b"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"