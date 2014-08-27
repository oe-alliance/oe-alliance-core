KV = "3.9.7"
SRCDATE = "20140826"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "fd39c1f16902d8862b77e6ff2cfa7f77"
SRC_URI[sha256sum] = "07859aa70d4cc45cb0a3bbd065bc941b662b0c18731bbad8b574df0e9b6eaa95"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
