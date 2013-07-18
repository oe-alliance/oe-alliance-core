KV = "3.6.0"
SRCDATE = "20130719"

SRC_URI[md5sum] = "b5324426132b43412a34826eaab64533"
SRC_URI[sha256sum] = "e6878e5ab22c22d97013cbbfb3aeaf7dbe242d001182c821f3c1cbf8f8b69a89"

SRC_URI = "http://code-ini.com/software/drivers/ini-1000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhde"
RCONFLICTS = "venton-dvb-modules-ventonhde"

require ini-dvb-modules.inc

