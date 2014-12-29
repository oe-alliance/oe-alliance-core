KV = "3.14.21"
SRCDATE = "20141226-r2"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "29e360a67a3a930c14c19945844322a7"
SRC_URI[sha256sum] = "068f8b496b767a5f4ca9fa2fe8ae31dfaf63ddb73cb9a0669af32cf0537f6ba5"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
