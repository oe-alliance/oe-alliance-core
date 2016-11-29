KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161128"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "9734e85d546bd230413bbc144d80acba"
SRC_URI[sha256sum] = "5f13f7690270519608812ae6ef38d55440d723ca3c3c33136e7a5b13c9766b6c"
