KV = "3.6.0"
SRCDATE = "20130716"

SRC_URI[md5sum] = "351bb0c66a36ca96888400c6929408a9"
SRC_URI[sha256sum] = "4af46b15a4c1c646452a6a217ff5e7edc14b46d9cff88d1e1b8d347e659e78f2"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
