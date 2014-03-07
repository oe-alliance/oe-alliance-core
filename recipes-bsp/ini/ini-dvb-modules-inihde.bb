KV = "3.6.0"
SRCDATE = "20140307"

SRC_URI[md5sum] = "db0fcf71e42edea1c8348897387d9431"
SRC_URI[sha256sum] = "289797d616967276276eac0db38ed0fe12d2029c35381686ef63da857ec6b94b"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

