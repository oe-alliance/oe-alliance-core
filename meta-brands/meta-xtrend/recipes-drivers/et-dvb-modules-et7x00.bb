KV = "4.4.8"
GCC = "5.3.0"
SRCDATE = "20161011"

SRC_URI = "http://www.xtrendet.net/${MACHINE}-drivers-${KV}-${GCC}-${SRCDATE}.zip"

require et-dvb-modules.inc

SRC_URI[md5sum] = "323bbec09adf15c306e7172c0699bded"
SRC_URI[sha256sum] = "07dacb82a423aea1a0236190f63a89072071517c7356361a016892d8bef7f1eb"
