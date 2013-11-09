KV = "3.6.0"
SRCDATE = "20131107"

SRC_URI[md5sum] = "3dd44db094757eeb3d4c1578e766a058"
SRC_URI[sha256sum] = "600c2eed65f4048884f8cd3ecdb1359eca338540dfc2c35fc47b088693ae2665"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

