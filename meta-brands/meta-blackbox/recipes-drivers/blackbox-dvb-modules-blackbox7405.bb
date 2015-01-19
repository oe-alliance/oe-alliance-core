KV = "3.14.21"
SRCDATE = "20150109-r3"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "2c0a72a8a6a2bf6b23f953e7e49cea49"
SRC_URI[sha256sum] = "c9f3300aaf8a546a8b0894da6a335fe5a98936c8361e01c8626bd05778825cfe"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
