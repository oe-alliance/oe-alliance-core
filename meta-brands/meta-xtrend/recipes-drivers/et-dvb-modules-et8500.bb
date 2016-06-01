KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160601"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "bf2c8ece4701f595ad0ca088114f97f2"
SRC_URI[sha256sum] = "5a71314ca9ec15ae15b5a92b398f53c200f528e9bbac67dd03ffea565bd17f56"
