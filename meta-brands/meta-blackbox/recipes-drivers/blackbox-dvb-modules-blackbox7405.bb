KV = "3.9.7"
SRCDATE = "20141007"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "48934a292cb755ba2114a9695d3bc7c9"
SRC_URI[sha256sum] = "f74fed38ec376fec8700ed17d9b0c8afb6df492ab89630f2c937d0d78b38deae"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
