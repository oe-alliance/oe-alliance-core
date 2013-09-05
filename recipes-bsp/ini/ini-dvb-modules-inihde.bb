KV = "3.6.0"
SRCDATE = "20130905"

SRC_URI[md5sum] = "a3f367ca08d7e96ee346ed8caf3deeb6"
SRC_URI[sha256sum] = "5597972ddf08e8fc0337c3f4476be1ae5dc2772326491b857160d2fa064f49d6"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

