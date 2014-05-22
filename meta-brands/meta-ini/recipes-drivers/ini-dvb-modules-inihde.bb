KV = "3.6.0"
SRCDATE = "20140505"

SRC_URI[md5sum] = "f5cffee1d962d6bc616c863315c1e75f"
SRC_URI[sha256sum] = "88e52b60c2059336b2c00806ead3d26b7354d06c8b4b56a4192bd79ef29a0842"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

