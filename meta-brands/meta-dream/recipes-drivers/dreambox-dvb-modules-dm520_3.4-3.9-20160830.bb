KV = "3.4-3.9"
DRIVERDATE = "20160830"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "3f80bb4d6780e0c84faa4558c011a715"
SRC_URI[dm520.sha256sum] = "d7c15e345ddd8b15459719cc3c166ec93681fad04712faa8126b34d9e9f7a3a6"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"