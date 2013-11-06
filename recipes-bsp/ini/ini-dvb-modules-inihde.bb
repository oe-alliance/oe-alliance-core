KV = "3.6.0"
SRCDATE = "20131105"

SRC_URI[md5sum] = "5c52c6968e43790f8a1cafbbf66d8013"
SRC_URI[sha256sum] = "425130eced1057bc2de32062c8e4767bf2836a982285552490ae50c714f1c5e2"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

