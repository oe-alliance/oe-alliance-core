KV = "3.4-3.0"
DRIVERDATE = "20150115"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "7d9f918517f579b251e724a1ec00c446"
SRC_URI[dm820.sha256sum] = "868cdad79432f5407fb50952ec6795d68c3bfccb8cf88784754f3ef186520562"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"