KV = "3.6.0"
SRCDATE = "20140211"

SRC_URI[md5sum] = "870e57bf648a464bfdef57ec8de2c58b"
SRC_URI[sha256sum] = "ea6d081e1ab713d3a47ec3c6cc0668f28f8d190cd4831fba215aff773a54194f"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES_${PN} = "venton-dvb-modules-ventonhde"
RCONFLICTS_${PN} = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

