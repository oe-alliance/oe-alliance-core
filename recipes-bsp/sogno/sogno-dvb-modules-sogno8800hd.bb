KV = "3.9.7"
SRCDATE = "20140319"

RDEPENDS_${PN} += "sogno-mac-check"

SRC_URI[md5sum] = "e749be589ec9819323286b94a4f0bc77"
SRC_URI[sha256sum] = "1ec8ef7baac87bcffdd4c75bb617a64376aee7114e3358fa0e0b5f34eb246ff2"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require sogno-dvb-modules.inc
