KV = "3.4-3.0"
DRIVERDATE = "20150119"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "fd73723e60f1a42074095feb5cad848c"
SRC_URI[dm820.sha256sum] = "b76416409405bfbb276c717e3d1543748396bfe209eeeb354313e388295eeff5"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"