KV = "3.9.7"
SRCDATE = "20141126"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "d4795240a3c3320737f2ebe16e46b7c3"
SRC_URI[sha256sum] = "9c6ff28950df400e86391e74993dbd4f697605db040b3f194eae098b734dd823"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
