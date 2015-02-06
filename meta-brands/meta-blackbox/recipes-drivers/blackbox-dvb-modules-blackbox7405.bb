KV = "3.14.21"
SRCDATE = "20150206"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "83d17d73316444267d36222e9a529b82"
SRC_URI[sha256sum] = "090f241a531f5d20487c0c7bffea36748f117d10191221c835c706be7cf1d989"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
