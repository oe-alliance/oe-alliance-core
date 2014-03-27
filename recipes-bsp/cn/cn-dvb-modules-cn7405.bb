KV = "3.9.7"
SRCDATE = "20140328-2"

RDEPENDS_${PN} += "cn7405-mac-check"

SRC_URI[md5sum] = "4c7aa788ff836f9547ff5013d888d8d3"
SRC_URI[sha256sum] = "eb0d1e91d78022ca9d38eecc8da813926d01a257facdad3f6652ffd4a8733e59"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require cn7405-dvb-modules.inc
