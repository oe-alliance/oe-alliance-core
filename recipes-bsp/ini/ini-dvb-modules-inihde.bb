KV = "3.6.0"
SRCDATE = "20131227"

SRC_URI[md5sum] = "60a81127c5a7c003ed49608bcd66babb"
SRC_URI[sha256sum] = "e9a980e9b8a9d6d218841cf9ee30e40c99319083ba00a7a362aa7afa554e1eb7"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

