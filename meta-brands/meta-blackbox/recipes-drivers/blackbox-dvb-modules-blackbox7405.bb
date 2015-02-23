KV = "3.14.21"
SRCDATE = "20150222"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "0dc270283cb6ed73dd5974abb6ac0141"
SRC_URI[sha256sum] = "0b24b16a7393edaf17bd1fcff30eaf68bd6066c6fbc34ee831ff883972390c0d"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
