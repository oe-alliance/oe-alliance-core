KV = "3.14-1.15"
DRIVERDATE = "20161209"

require dreambox-dvb-modules-dm900.inc

SRC_URI[dm900.md5sum] = "bce90773722f00c88ca28d15cd45b5d2"
SRC_URI[dm900.sha256sum] = "cddc1ad738fddcd679e5a7f58f44b965f713b5d48735b50e1dbbc338b9b8bec7"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm900)$"
