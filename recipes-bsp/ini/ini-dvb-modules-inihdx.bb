KV = "3.6.0"
SRCDATE = "20130711"

SRC_URI[md5sum] = "124a9311c2f124d5f5c0f718748d70a8"
SRC_URI[sha256sum] = "a5e51e767ed7148e0609e86f6ab281f2e3d2dd1b5f374b78e8b2864de27ecd59"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
