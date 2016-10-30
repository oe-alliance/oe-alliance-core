KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161027"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "a914f9f955c048987f660858c6d197ad"
SRC_URI[sha256sum] = "932804a894fefc8380f1ebedc95a2eb586eb36690d802643c49095b1017cb902"
