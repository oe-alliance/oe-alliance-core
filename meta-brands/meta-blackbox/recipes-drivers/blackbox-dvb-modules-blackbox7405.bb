KV = "3.14.21"
SRCDATE = "20151111"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "a5663f4325104afc9877309cd75d2175"
SRC_URI[sha256sum] = "c6f9a76526c297e7fef1f0482dd2df76aa5d2d97cad21e6343d7dc8985081641"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
