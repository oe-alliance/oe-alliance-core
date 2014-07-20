KV = "3.14.2"
SRCDATE = "20140716"

SRC_URI[md5sum] = "8d3e75e29c86106699b517648f7f26ec"
SRC_URI[sha256sum] = "a9344701e2d1e0bed8888e5ee4621d97076c863850530cb884de6cf9133c1771"

SRC_URI = "http://code-ini.com/software/drivers/ini-hdp-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
