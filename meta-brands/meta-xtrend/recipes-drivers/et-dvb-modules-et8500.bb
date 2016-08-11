KV = "4.4.8"
GCC = "4.9.1"
SRCDATE = "20160715"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc


SRC_URI[md5sum] = "e0f90550c1fb3a0bdfbe706685215a0f"
SRC_URI[sha256sum] = "294773a33dc9db743415f0beec22603a8bbab4d0dcf4df42b6e156b8dc84de70"


