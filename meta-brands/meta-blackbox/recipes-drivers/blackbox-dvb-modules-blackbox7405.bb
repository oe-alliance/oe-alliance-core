KV = "3.9.7"
SRCDATE = "20140929"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "630a2105991e8f70df15ed3f736fec71"
SRC_URI[sha256sum] = "c7a134e64d6f8e8c474f6176d1fe45a6b8efeb1940ede45b6bd02cd87ff699e4"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
