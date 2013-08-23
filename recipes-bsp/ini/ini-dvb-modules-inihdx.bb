KV = "3.6.0"
SRCDATE = "20130823"

SRC_URI[md5sum] = "85f0ccd75a15c67b4500beed21d97e8a"
SRC_URI[sha256sum] = "ee6b1291145923a2d7d572782950434a8877c7b8b6a06d2e387d3f4a806af17b"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
