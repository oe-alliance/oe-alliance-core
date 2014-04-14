KV = "3.9.7"
SRCDATE = "20140411"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "9c4d657790e4812192cf329cd67f19a8"
SRC_URI[sha256sum] = "90248bc32760d7efaeba6c090cbef21c2ae3645e823fc28fc78150a7c6c11454"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
