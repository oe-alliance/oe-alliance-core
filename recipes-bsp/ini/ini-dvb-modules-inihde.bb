KV = "3.6.0"
SRCDATE = "20130718"

SRC_URI[md5sum] = "b96876f401c36d3adb5eb0c0e98ad45e"
SRC_URI[sha256sum] = "b92efe0061e882d1950fb763838be905614acbbeeac650ac5e85355da74e25a9"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

