KV = "3.9.7"
SRCDATE = "20140717"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "02b2c5fde20b4d15417123fadaec457f"
SRC_URI[sha256sum] = "8c95f0b45a6abd5c9b717e0c845d126a5c2c2192324c320e24f8ca2b68857857"

SRC_URI = "http://pluginvalley.kr/bcm/driver/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
