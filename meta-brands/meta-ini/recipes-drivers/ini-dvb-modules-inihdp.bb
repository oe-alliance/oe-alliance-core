KV = "3.14.2"
SRCDATE = "20140829"

SRC_URI[md5sum] = "f26768a22fc8ee7c60a54c94a79dd6dd"
SRC_URI[sha256sum] = "adb5ebcd5c4cfe2e37f4a9573347d6ff094ac4df391357f5e1aee41ed64602f8"

SRC_URI = "http://code-ini.com/software/drivers/ini-hdp-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
