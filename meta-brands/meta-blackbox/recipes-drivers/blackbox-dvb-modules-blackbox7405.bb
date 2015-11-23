KV = "3.14.21"
SRCDATE = "20151118"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "9d78c5c74cf131ee3a5f85a31dde6185"
SRC_URI[sha256sum] = "c00bce20037981c0e6212db44c7bd5756d28627e53321dee8aea8e9db9348452"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
