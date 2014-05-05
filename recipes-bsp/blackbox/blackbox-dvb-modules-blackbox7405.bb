KV = "3.9.7"
SRCDATE = "20140501"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "3ba853bd6f4641271f5382fd68dfcbde"
SRC_URI[sha256sum] = "d11fed862670dd6e9b2a205bd767f49b1e1b23e2a8e590b9f9ed50e1596fa4a9"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
