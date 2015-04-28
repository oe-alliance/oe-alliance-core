KV = "3.14.21"
SRCDATE = "20150425"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "dbd1bc2e5a544e37d9a82132940c6a4a"
SRC_URI[sha256sum] = "71d535257787c752241dd31cc78369caa6c467dc8d7fde2be4ce67e7257cc2f2"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
