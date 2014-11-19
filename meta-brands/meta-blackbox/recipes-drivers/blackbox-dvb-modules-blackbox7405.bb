KV = "3.9.7"
SRCDATE = "20141111-r3"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "3a8c11a04941082566a6e58c7b50f6c6"
SRC_URI[sha256sum] = "6c741185d159ca2bb0cc7e62ebc108ffa8d1905b8c821c272156dc27dd6a2a53"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
