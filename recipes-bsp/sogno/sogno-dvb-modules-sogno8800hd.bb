KV = "3.9.7"
SRCDATE = "20140326"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "8ef1f3c69844463b626ced78a3b658c3"
SRC_URI[sha256sum] = "944aa83133e6f63b5d0f3629da85ca63826bf1821e466a594555f0d856c9909b"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require sogno-dvb-modules.inc
