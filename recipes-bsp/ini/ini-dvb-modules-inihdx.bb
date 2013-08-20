KV = "3.6.0"
SRCDATE = "20130808"

SRC_URI[md5sum] = "32d1fc49e27a514e4c192d5f95ccacc1"
SRC_URI[sha256sum] = "dde7b047451a0e38a951dad8083f8b1c7f6076b4965d62f58294d0a6c7921d17"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhdx"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
