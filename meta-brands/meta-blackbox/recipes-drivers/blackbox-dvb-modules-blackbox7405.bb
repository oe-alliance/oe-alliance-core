KV = "3.9.7"
SRCDATE = "20141014"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "9c3d09ab6a5032c107f54fb48a2de40e"
SRC_URI[sha256sum] = "05401329d76e69f1da006fbbae77e3e4e6913704b2be9789cea2b6c8fd931b4d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
