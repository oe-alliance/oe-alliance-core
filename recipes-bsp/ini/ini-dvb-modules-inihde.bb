KV = "3.6.0"
SRCDATE = "20131120"

SRC_URI[md5sum] = "6d900853bdec21f78c978254317429ae"
SRC_URI[sha256sum] = "f2361b425f4c7b870ae1f0ad4e6c2cf1f54356b050b25c29a67d758a35197d25"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

