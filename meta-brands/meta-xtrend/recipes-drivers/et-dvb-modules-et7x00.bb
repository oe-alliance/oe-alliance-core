KV = "4.4.8"
GCC = "4.9.1"
SRCDATE = "20160705"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "39e8f3463770136fc67546e6b6d780ca"
SRC_URI[sha256sum] = "10b5bdb96c1adcc75acae0cbd51b6af495caf1d62534e40352c93add5399323e"
