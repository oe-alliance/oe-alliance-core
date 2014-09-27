KV = "3.9.7"
SRCDATE = "20140926"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "d005afc6f8155d9e2cd8d1a594742fbf"
SRC_URI[sha256sum] = "ef09b2c5ee26e5e2d17f47c69844be5b77d75d730fa1fabf79b7bb587b721c45"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
