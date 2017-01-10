KV = "3.4-4.0"
DRIVERDATE = "20170110"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "41d4b1cacd67b64009860e191ba06899"
SRC_URI[dm520.sha256sum] = "e04dbbc411620fe9cb8e4f4d430cedc684a31a4c504bcf64893ea029205376fa"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"