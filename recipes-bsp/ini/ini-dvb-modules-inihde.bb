KV = "3.6.0"
SRCDATE = "20130711"

SRC_URI[md5sum] = "822891550e66b7f693000a65543dfb51"
SRC_URI[sha256sum] = "3b36a530e0c0bb55541483ca841d72870bbd8d69c2e127d9d6c2fc33f58ce418"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

