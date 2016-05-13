KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160504"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "8f112a427a76dc7ab5c36cb9b572d348"
SRC_URI[sha256sum] = "949184f2216d4827d45b41b0731ae11db4ef330a37c6c56c02928926c5176b10"
