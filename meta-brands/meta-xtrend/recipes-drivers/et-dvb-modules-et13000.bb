KV = "4.1.20"
SRCDATE = "20170628"

SRC_URI = "https://peteru.net/xtrend/${MACHINE}-drivers-${KV}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "86a64b7d5cd37965cfb55b607a048543"
SRC_URI[sha256sum] = "0ffa3aa7f003d273e6b73ce1e1e6e3c948dd8a7841cf5d66d98f8fad27ceb0e7"
