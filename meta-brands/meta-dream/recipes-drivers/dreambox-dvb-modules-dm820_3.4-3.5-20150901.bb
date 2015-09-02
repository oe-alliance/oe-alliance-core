KV = "3.4-3.5"
DRIVERDATE = "20150901"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "bdc8adb7d4d9d644153e753d39686c96"
SRC_URI[dm820.sha256sum] = "84953851d3bc32ff210ff082407b8dc190ae19a5fa97b1112c22ad99bf054ca1"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"