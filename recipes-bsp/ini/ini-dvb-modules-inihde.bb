KV = "3.6.0"
SRCDATE = "20131002"

SRC_URI[md5sum] = "2df5703b251b550ef26b18558ff1fc7c"
SRC_URI[sha256sum] = "6f498b4d713fbca290264e5fb602d9e2474e4cbda7c396050bf3b28b755fa3fb"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

