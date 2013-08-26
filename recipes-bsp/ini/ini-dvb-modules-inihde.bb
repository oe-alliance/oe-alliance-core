KV = "3.6.0"
SRCDATE = "20130823"

SRC_URI[md5sum] = "8152a917516e17ffae6fc6920aefc367"
SRC_URI[sha256sum] = "252d7f4eb54d184f48afd9e9f97ec3a016c5021d5fb3d3dae29ae7107925a3d9"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

