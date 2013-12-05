KV = "3.6.0"
SRCDATE = "20131202"

SRC_URI[md5sum] = "7d11c12a80255fc60475594ef2730b3f"
SRC_URI[sha256sum] = "83d35c619270c22cde2808f6f9115c9532db2c9ecf94af9636e4b1bf881205d8"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

