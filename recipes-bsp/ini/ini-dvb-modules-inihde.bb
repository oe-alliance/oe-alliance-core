KV = "3.6.0"
SRCDATE = "20130820"

SRC_URI[md5sum] = "f11b1fc65ba4b2779e2e5af72ae07ebc"
SRC_URI[sha256sum] = "6dc4e918e33f3be6b010f2c6d0674df05e381061641f7986e4eb1fb58e8d7933"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

