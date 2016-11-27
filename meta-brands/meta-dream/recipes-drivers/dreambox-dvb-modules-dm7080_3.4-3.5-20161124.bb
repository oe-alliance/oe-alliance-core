KV = "3.4-3.5"
DRIVERDATE = "20161124"

require dreambox-dvb-modules-dm7080.inc

SRC_URI[dm7080.md5sum] = "036a2e9589797cfedc1f016dff67cd00"
SRC_URI[dm7080.sha256sum] = "634131560086a001efe70ed71afa9ee208f829ba378bbb62c0a1aa0d750b4dd2"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm7080)$"