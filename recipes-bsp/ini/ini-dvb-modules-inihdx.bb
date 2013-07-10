KV = "3.6.0"
SRCDATE = "20130709"

SRC_URI[md5sum] = "681b5f4fd9c2416a4f7a92d05a1e80b1"
SRC_URI[sha256sum] = "9a963b8fe8527218ff2cc9992e362d3495b4985123f6e9e91feee3a2b0d0159f"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
