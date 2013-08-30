KV = "3.6.0"
SRCDATE = "20130830"

SRC_URI[md5sum] = "6a5b1104aa9d42ce8f369ddd160617eb"
SRC_URI[sha256sum] = "43bc4015650ce5418fddb43ff4141b16a485475c921818a990c25660863a84c3"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

