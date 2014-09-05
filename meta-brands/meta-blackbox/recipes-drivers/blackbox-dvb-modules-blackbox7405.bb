KV = "3.9.7"
SRCDATE = "20140905"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "ca893e9b1204fdd118e149bf15e4d184"
SRC_URI[sha256sum] = "01ed5f12297bc6a63dbe4ed74f89813b9ed70602747cdc252d8df01378207cb4"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
