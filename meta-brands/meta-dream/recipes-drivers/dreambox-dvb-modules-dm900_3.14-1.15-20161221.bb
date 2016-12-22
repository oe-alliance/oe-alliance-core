KV = "3.14-1.15"
DRIVERDATE = "20161221"

require dreambox-dvb-modules-dm900.inc

SRC_URI[dm900.md5sum] = "bda41241253b59d0a1d9f481900670fc"
SRC_URI[dm900.sha256sum] = "e6349be537d03915a30fb8c44799f334958e91e43f05e7339bd5d4bf975a8a0d"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm900)$"
