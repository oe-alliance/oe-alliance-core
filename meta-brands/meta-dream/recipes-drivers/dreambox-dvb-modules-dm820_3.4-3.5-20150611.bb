KV = "3.4-3.5"
DRIVERDATE = "20150611"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "e12b8e763d167375d8f5500e37244cb7"
SRC_URI[dm820.sha256sum] = "09c38069a650e33c76475a7061679bfec10a77779444c4018fc8454f22527270"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"