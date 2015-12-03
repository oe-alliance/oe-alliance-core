KV = "3.4-3.5"
DRIVERDATE = "20151202"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "6d7a94a9e9fce44caaaadb3971eb981e"
SRC_URI[dm7080.sha256sum] = "b5ebf1851d53473d005d34863fa8a8bf26a754301861f0e652edf1eab57b6304"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"