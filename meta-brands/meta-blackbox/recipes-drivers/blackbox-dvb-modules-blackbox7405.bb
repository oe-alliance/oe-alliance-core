KV = "3.9.7"
SRCDATE = "20140805"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "6f63936c43d7d8310d63a48d95faa07c"
SRC_URI[sha256sum] = "ea94d9e000ddac368d911363e4459e0b23407e07788b3c12c02d3e31c3ce36c5"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
