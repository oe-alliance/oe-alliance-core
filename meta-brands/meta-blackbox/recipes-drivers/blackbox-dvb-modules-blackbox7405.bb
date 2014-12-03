KV = "3.9.7"
SRCDATE = "20141126"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "e7c8862550e40f8b85a045557973b9d5"
SRC_URI[sha256sum] = "3f49eceb96eac4fdd7c6c6b3562746b5b1f1375700eb8b6c92afadf09d3ccbae"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
