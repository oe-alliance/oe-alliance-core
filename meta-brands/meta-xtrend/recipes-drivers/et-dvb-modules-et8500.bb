KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20160624"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "516cf17b77612cd1a8de777d68549458"
SRC_URI[sha256sum] = "f9953f14bdc9467351ed7c9c44a3757fed2f1b01d458d40bd81a60612daca7b1"
