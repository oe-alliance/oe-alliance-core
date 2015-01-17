KV = "3.4-3.0"
DRIVERDATE = "20150115"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "593c200e202d55560cc50826980a2e2c"
SRC_URI[dm7080.sha256sum] = "dfe31a632ee9b8882b8943e98b5e1875a6ea79bc8840dde08bbd05bbfcc26881"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"