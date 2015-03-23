KV = "3.14.21"
SRCDATE = "20150323"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "2ccbe9e98688ac159f9d01ab5ae29087"
SRC_URI[sha256sum] = "efa6bb899aa51bba148b3e6f2bc8da99ee5fd93c3bb22b6896d52bbdb4513e7c"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
