KV = "3.6.0"
SRCDATE = "20140220"

SRC_URI[md5sum] = "83efa38ab64ff07d0bebdf0a387a739d"
SRC_URI[sha256sum] = "14d063b36e2bc02b3ac057044af3b14e8c879fcab47d95d0fe7b1b0a9d7e16bd"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

