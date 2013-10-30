KV = "3.6.0"
SRCDATE = "20131025"

SRC_URI[md5sum] = "009653efd4a0b4f60a1003b867cf1ea9"
SRC_URI[sha256sum] = "a733443dbd3214bc90b72b5019568019f4af4a0b5ba2a0bcbd3e1e9907086866"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

