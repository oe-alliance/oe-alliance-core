KV = "3.6.0"
SRCDATE = "20130723"

SRC_URI[md5sum] = "684bdf47a873e13a99a61706c1434d35"
SRC_URI[sha256sum] = "9695e57c52beae645815b6ed5f287c43fd1bedfdda4102ce8d7c1bc54dfac191"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

