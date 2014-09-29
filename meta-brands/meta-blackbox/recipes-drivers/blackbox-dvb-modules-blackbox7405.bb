KV = "3.9.7"
SRCDATE = "20140928"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "2c8df5dfdc31bc04dc3092ae7a5db0c7"
SRC_URI[sha256sum] = "eb9b33a68c268e5c172ccc9e20c48570f80e2e5ec81cb01963a1438ba24b5892"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
