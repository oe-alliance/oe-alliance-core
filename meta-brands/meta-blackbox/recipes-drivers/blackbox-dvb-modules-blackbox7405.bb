KV = "3.14.21"
SRCDATE = "20150219"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "c9e8f37ace1d472a4b9f636570580523"
SRC_URI[sha256sum] = "81907923529d0046ee799d6e4c22ad725726d2d2cda0d46d811b3e7691ca8143"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
