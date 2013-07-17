KV = "3.6.0"
SRCDATE = "20130717"

SRC_URI[md5sum] = "532173850f9afe68c8971deda2cbf335"
SRC_URI[sha256sum] = "ca426396f0158b2ae8106086a449c52124d1e2ed438e9496a96db04e42e9a267"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

