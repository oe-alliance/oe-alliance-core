KV = "3.9.7"
SRCDATE = "20140913"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "3b705a66c96b8e2c189a75f1ab647505"
SRC_URI[sha256sum] = "e696af98f823e9283c1d3827e98551408a1e4b427bb0ee5cc303ad7bc27c8ec0"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
