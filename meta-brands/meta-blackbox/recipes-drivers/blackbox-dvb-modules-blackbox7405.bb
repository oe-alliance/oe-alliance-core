KV = "3.14.21"
SRCDATE = "20150327"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "7b84b214bc7e11aa859ebe19e24a4896"
SRC_URI[sha256sum] = "5e424754db14e73fb87c80276091916658e9cde3df4d4e842d7056376fa5643c"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
