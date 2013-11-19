KV = "3.6.0"
SRCDATE = "20131119"

SRC_URI[md5sum] = "e86b8902b78fd81cb9327ec6cae9e8d7"
SRC_URI[sha256sum] = "3f55321196ee8ef5604f60c4ec1306183e7f7653a8f822e6b729f0b81eaf954d"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
