KV = "3.4-3.0"
DRIVERDATE = "20150127"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "347ec0845cb71513ad282c1cd9308835"
SRC_URI[dm820.sha256sum] = "3deb7bc9a5ccd6f6fec98faa67a142978552058e4550db22f3c6500518602a6f"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"