KV = "3.6.0"
SRCDATE = "20140319"

SRC_URI[md5sum] = "62bf02deba1fd197daf1a2220afcc991"
SRC_URI[sha256sum] = "e042e800024be0b7210931f04a13c2c6e409a214b05059b97b289d89b108eac4"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhdx"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
