KV = "3.9.7"
SRCDATE = "20140517"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "69b944fe92a836354f809ff88f882752"
SRC_URI[sha256sum] = "12d4e9b52a13240fe6e33dc87bc6d4bb3818b2253a93abf987d69c501ffda259"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
