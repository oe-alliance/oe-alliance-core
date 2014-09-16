KV = "3.9.7"
SRCDATE = "20140916"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "e7ccb24dfa85922a5cd3bd1f9fdb5cac"
SRC_URI[sha256sum] = "1afdf8055a1bd3af764d3c1e11adee35923417990503617a6ff9f9b7b2bb568d"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
