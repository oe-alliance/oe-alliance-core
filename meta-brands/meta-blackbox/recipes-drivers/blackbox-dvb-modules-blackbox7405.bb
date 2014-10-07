KV = "3.9.7"
SRCDATE = "20141001"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "9f260220a3075a12d9f3241123b1bec3"
SRC_URI[sha256sum] = "8daebbdd5584d73ddc55854e5d568b3cf8c4dc0e3eafb1db77b3b24d14d61249"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
