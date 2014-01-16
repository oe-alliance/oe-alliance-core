KV = "3.6.0"
SRCDATE = "20140116"

SRC_URI[md5sum] = "7f6a4d4d213a7f005077d7e02d03855d"
SRC_URI[sha256sum] = "a2d10d54343189ec940be566f9b077ee4c20cd3d54d8ffa391a82fe6ea872c5a"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
