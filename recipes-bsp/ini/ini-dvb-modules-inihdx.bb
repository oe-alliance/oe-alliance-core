KV = "3.6.0"
SRCDATE = "20131029"

SRC_URI[md5sum] = "9829379265f4be1a660f5632478099af"
SRC_URI[sha256sum] = "570253f6ec026c6e2f335d542b2f4e501543bf653d07db782d3e2764bc804d71"

SRC_URI = "http://code-ini.com/software/drivers/ini-x000-drivers-${KV}-${SRCDATE}.zip"

RREPLACES = "venton-dvb-modules-ventonhdx"
RCONFLICTS = "venton-dvb-modules-ventonhdx"

require ini-dvb-modules.inc
