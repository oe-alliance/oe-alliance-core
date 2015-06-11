KV = "3.14.21"
SRCDATE = "20150611"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "5b260d9bba1aa19e78c841292eb835c8"
SRC_URI[sha256sum] = "112a23e365ea57348c1f7a8d5ea35fda835459562c3f47ad9082ece143adf233"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
