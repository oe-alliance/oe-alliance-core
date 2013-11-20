KV = "3.6.0"
SRCDATE = "20131120"

SRC_URI[md5sum] = "4fb49a7197ef573f68cbf95de3be37bd"
SRC_URI[sha256sum] = "8efcaebafc61c24dd5f56a343185db8ec5e7c65cf98b474f189a3caa12459890"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
