KV = "3.4-3.5"
DRIVERDATE = "20150618"

require dreambox-dvb-modules-dm820.inc

SRC_URI[dm820.md5sum] = "74e382906cb8840dba4e17c247cb8751"
SRC_URI[dm820.sha256sum] = "a092cd7c6e928aacda8bc3023b10736cc1fb24727eca79100cafce796ef286a6"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${DM_LOCALVERSION}
fi

COMPATIBLE_MACHINE = "^(dm820)$"