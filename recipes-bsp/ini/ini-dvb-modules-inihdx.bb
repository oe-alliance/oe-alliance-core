KV = "3.6.0"
SRCDATE = "20131205"

SRC_URI[md5sum] = "d54adfd613b08a007158aad98cdcd019"
SRC_URI[sha256sum] = "8a5057e5f6f4f67a6bf30d6cb06892b4ae7c0de8d22b9cdfea71fde80f1d66f2"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
