KV = "3.14.21"
SRCDATE = "20150720"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "a6f58c857dc14f898693ec5d3f137d9b"
SRC_URI[sha256sum] = "80ad62f545b6a02fc7ff30a8e28c7c3720a0fd6960e72eaf8dc5a0a6647aa908"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
