KV = "3.14.21"
SRCDATE = "20150228"

RDEPENDS_${PN} += "blackbox7405-mac-check"

SRC_URI[md5sum] = "7b5986f723b9538492d8167481e0090c"
SRC_URI[sha256sum] = "dc1f2914b0c870160a5ef4ac801b1b7b619e733e338097755a641fa857fc4e46"

SRC_URI = "http://unibox.to/feeds/eco/development/drivers/linuxdvb_7405-${KV}-${SRCDATE}.tar.gz"

require blackbox7405-dvb-modules.inc
