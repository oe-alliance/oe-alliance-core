KV = "3.4-3.9"
DRIVERDATE = "20161126"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "7f301c6b0e6b41a43cc9d7c752835a33"
SRC_URI[dm520.sha256sum] = "da9a20f43f2d94af04caa5d5c930df406e5621a5d415f39d009aabc1c126d459"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"