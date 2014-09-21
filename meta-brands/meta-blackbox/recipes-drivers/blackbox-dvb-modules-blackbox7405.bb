KV = "3.9.7"
SRCDATE = "20140920"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "86fdb435ab03a3a74f1720d62efbc746"
SRC_URI[sha256sum] = "eb3a9013dc19fd35ec3919d42184d06fffc9c1e856bb11d130b0d0e53e566bf4"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
