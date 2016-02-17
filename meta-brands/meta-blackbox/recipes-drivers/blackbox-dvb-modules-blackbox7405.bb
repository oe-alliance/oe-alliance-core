KV = "3.14.21"
SRCDATE = "20160128"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "5802811d439b2b91894bc2333b5528e8"
SRC_URI[sha256sum] = "4662f25ebb46c5d4961aeca3e3d692f86a93e09070c70d565f19d52e0cae6891" 

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
