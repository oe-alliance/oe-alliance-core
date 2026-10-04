PV = "4.1.45"
KV = "${PV}-1.17"
SRCDATE = "20250706"
SRCDATE_PR = "r2"

require vuplus-dvb-proxy.inc

SRC_URI[md5sum] = "24cb50123a8a9d4ebba56d794dd13880"
SRC_URI[sha256sum] = "8ee2fa218e78035440a49d72dd29c0224cf29d4d18da052dd0de90dae382f4c6"

require vuplus-stcbind-boot.inc
