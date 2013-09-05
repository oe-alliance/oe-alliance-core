KV = "3.6.0"
SRCDATE = "20130905"

SRC_URI[md5sum] = "b9576cbc02e344cc006eff37adcb4af8"
SRC_URI[sha256sum] = "a1e9f3d70a299876c0eb35e572521e1108501fc64101681ba14d437fccbd52c5"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
