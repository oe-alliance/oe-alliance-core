KV = "3.4-3.5"
DRIVERDATE = "20150911"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "74134fb2a43d7a3e18767c419730311f"
SRC_URI[dm820.sha256sum] = "0d268c10e72ef10a3a7b6df5e419ca75685c935814fab2f0dcfe08f80d9021c7"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"