KV = "3.9.7"
SRCDATE = "20140708"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "08f5d0739ec6ad180366e99ab032b5da"
SRC_URI[sha256sum] = "653704f810e397a4764fc8164541e6bc9694e4931f94548c97afa63092b8b6f3"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
