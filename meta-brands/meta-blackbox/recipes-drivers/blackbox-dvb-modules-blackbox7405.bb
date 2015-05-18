KV = "3.14.21"
SRCDATE = "20150517"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "e009db87e349c55ea74ab0be4192ab89"
SRC_URI[sha256sum] = "b1233386b39432a3eb91a765a3c3baaba9e8f2abb1fe24fbe8f229c99e2cb9ff"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
