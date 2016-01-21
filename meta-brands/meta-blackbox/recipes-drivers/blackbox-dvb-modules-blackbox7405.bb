KV = "3.14.21"
SRCDATE = "20160108"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "bc3e1e934fe7d4b2d6c9ba6d830f6d0e"
SRC_URI[sha256sum] = "0442ae0f6f34021a4bf52e17bffad5ddbd2777237966a41b2b1b02e182077ab3"  

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
