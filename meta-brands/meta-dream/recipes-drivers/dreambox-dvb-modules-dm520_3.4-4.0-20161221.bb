KV = "3.4-4.0"
DRIVERDATE = "20161221"

require dreambox-dvb-modules-dm520.inc

SRC_URI[dm520.md5sum] = "081fb795260cd106397890e6e57f5c8b"
SRC_URI[dm520.sha256sum] = "a535600765952212e7eb8a126f435b0c748a57c3c44b995462947329558996fa"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm520)$"