KV = "3.6.0"
SRCDATE = "20140603"

SRC_URI[md5sum] = "2cb1c6f95ce804416e806fe32fa009c7"
SRC_URI[sha256sum] = "5a9c83d88a306d0e13a66a66199a2e0e111c176f7022d75aee0915357f8eec25"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

