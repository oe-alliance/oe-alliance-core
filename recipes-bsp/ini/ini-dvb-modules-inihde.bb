KV = "3.6.0"
SRCDATE = "20131119"

SRC_URI[md5sum] = "7098062a8ea69db0e753abb565661972"
SRC_URI[sha256sum] = "1035f96689481d99c801b7e80a26a0ef6d29e2a0600d741c40d512c4a7c22c0d"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

