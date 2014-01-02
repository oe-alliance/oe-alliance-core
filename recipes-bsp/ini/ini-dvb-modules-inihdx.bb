KV = "3.6.0"
SRCDATE = "20140102"

SRC_URI[md5sum] = "1467647a974f45829d646aeb4a38aa97"
SRC_URI[sha256sum] = "f6318913e33d75bc9b29f657bdfca49f3062a9b6432d9ad41b56c91cf604f43d"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
