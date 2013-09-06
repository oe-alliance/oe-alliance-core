KV = "3.6.0"
SRCDATE = "20130902"

SRC_URI[md5sum] = "7201564a31fde31e12c1f4f92a699b79"
SRC_URI[sha256sum] = "f5ed5fbab4c03d5032bbb499b03d622f4cb17b6e9fa51c81956ac007fa290c08"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
