KV = "3.14.2"
SRCDATE = "20140701"

SRC_URI[md5sum] = "87c3ad1bfb93f16c9b4331774d8043fc"
SRC_URI[sha256sum] = "ced907c1674ddbeaf4691dc62a7c96ddf4ca298f1109d5467bfd87cd5099c23a"

SRC_URI = "http://code-ini.com/software/drivers/ini-hdp-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
